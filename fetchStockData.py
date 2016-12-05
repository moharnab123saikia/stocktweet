#!/usr/bin/env python
"""
Retrieve intraday stock data from Google Finance.
"""

import csv
import datetime
import re

import pandas as pd
import requests

from kafka import *
from urllib import urlretrieve
import datetime as dt
import os
import time
import sys

# kafka setup
mykafka = KafkaClient("localhost:9092")
producer = SimpleProducer(mykafka)
topicName = 'stockStream'
path = '/tmp/'


def get_google_finance_intraday(ticker, period=60, days=1):
    """
    Retrieve intraday stock data from Google Finance.
    Parameters
    ----------
    ticker : str
        Company ticker symbol.
    period : int
        Interval between stock values in seconds.
    days : int
        Number of days of data to retrieve.
    Returns
    -------
    df : pandas.DataFrame
        DataFrame containing the opening price, high price, low price,
        closing price, and volume. The index contains the times associated with
        the retrieved price values.
    """

    uri = 'http://www.google.com/finance/getprices' \
          '?i={period}&p={days}d&f=d,o,h,l,c,v&df=cpct&q={ticker}'.format(ticker=ticker,
                                                                          period=period,
                                                                          days=days)
    page = requests.get(uri)
    reader = csv.reader(page.content.splitlines())
    columns = ['Open', 'High', 'Low', 'Close', 'Volume']
    rows = []
    times = []
    for row in reader:
        if re.match('^[a\d]', row[0]):
            if row[0].startswith('a'):
                start = datetime.datetime.fromtimestamp(int(row[0][1:]))
                times.append(start)
            else:
                times.append(start+datetime.timedelta(seconds=period*int(row[0])))
            rows.append(map(float, row[1:]))
    df = pd.DataFrame()
    if len(rows):
        df =  pd.DataFrame(rows, index=pd.DatetimeIndex(times, name='Date'),
                            columns=columns)
    else:
        df =  pd.DataFrame(rows, index=pd.DatetimeIndex(times, name='Date'))

    df.to_csv(path+ticker+"New.csv", sep=',')

def produceData(ticker):
    # produce data to Kafka from reading from the csv file
    fileNameNew = path+ticker+"New.csv"
    with open(fileNameNew) as f1:
        next(f1, None)
        lineset = set(f1)

    print 'start writing to Kafka...'
    transformedLine = ''
    for lineT in lineset:
            line = lineT.split(',')
            if len(line)==6: # check for correctness
                newLine = [str(time.time()), ticker]
                transformedLine = ','.join(newLine) +','+','.join(line)
                #print(transformedLine)
                producer.send_messages(topicName,transformedLine)
            else:
                print "-----------------------------------" + str(len(line))


if __name__ == '__main__':
    get_google_finance_intraday(ticker = sys.argv[1],days = 15)
    produceData(sys.argv[1])

    # get_google_finance_intraday(ticker="AAPL",days = 1)
    # produceData("AAPL")
