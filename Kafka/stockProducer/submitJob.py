# -*- coding: utf-8 -*-
"""
this script submit stockProducer job for
each individual stocks
"""
import os.path
import sys

sys.path.insert(0, '../twitterProducer')
from cashtagSet import cashtagSet

cmd = 'nohup python ../../fetchStockData.py '
suffix = ' &'


def submitJob(stock):
    logFile = 'log' + stock + '.txt'
    cmdTotal = cmd + "'" + stock + "'" + ' > ' + logFile + suffix
    print cmdTotal
    os.system(cmdTotal)


def doWork():
    # stocks = cashtagSet('NASDAQ100')
    # for stock in stocks.split(','):
    #     submitJob(stock[1:])

    # stocks = cashtagSet('NYSE100')
    # for stock in stocks.split(','):
    #     submitJob(stock[1:])
    # #
    # stocks = cashtagSet('COMPANIES')
    # for stock in stocks.split(','):
    #     submitJob(stock[1:])

    stocks = cashtagSet('SP')
    for stock in stocks.split(','):
        submitJob(stock[1:])


if __name__ == '__main__':
    doWork()
