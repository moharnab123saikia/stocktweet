# -*- coding: utf-8 -*-
"""
this script submit stockProducer job for 
each individual stocks
"""
import os.path
import sys
sys.path.insert(0, '../twitterProducer')
from cashtagSet import cashtagSet



cmd = 'nohup python ./stockProducer.py '
suffix = ' &'

def submitJob(stock, exchange):
    logFile = 'log'+stock+'.txt'
    cmdTotal = cmd+"'"+stock+"'"+' '+"'"+exchange+"'"+' > '+logFile+suffix
    print cmdTotal
    os.system(cmdTotal)


def doWork():
    exchange = 'O'
    stocks = cashtagSet('NASDAQ100')
    print(stocks)
    for stock in stocks:
        ticker = stock
        submitJob(ticker, exchange)

    exchange = 'N'
    stocks = cashtagSet('NYSE100')
    for stock in stocks:
        ticker = stock
        submitJob(ticker, exchange)

    exchange = 'N'
    stocks = cashtagSet('COMPANIES')
    for stock in stocks:
        ticker = stock
        submitJob(ticker, exchange)


if __name__ == '__main__':
    doWork()
