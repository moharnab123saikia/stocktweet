# -*- coding: utf-8 -*-

import csv


def cashtagSet(Type):

    flag = 0
    # define the function blocks
    if Type=="DOW30":
        FILE_NAME = '../stockData/nasdaq100.csv'
    elif Type=="NASDAQ100":
        FILE_NAME = '../stockData/nasdaq100.csv'
    elif Type== "NYSE100":
        FILE_NAME = '../stockData/nyse100.csv'
    elif Type== "SP500":
        FILE_NAME = '../stockData/SP500.csv'
    elif Type =="SP":
        FILE_NAME='../stockData/SP500New.csv'
    elif Type== "NASDAQ_COMPOSITE":
        FILE_NAME = '../stockData/NASDAQComposite.csv'
    elif Type== "NYSE_COMPOSITE":
        FILE_NAME = '../stockData/NYSEComposite.csv'
    elif Type =="COMPANIES":
	    FILE_NAME='../stockData/companies.csv'
    elif Type== "ALL":
        FILE_NAME = '../stockData/allStocks.csv'
        flag = 1
    else:
        raise Exception("unknown stock type")

    with open(FILE_NAME,'rU') as file1:
        if flag==1:
            dat = csv.reader(file1,  dialect=csv.excel_tab, delimiter=',')
            idx = 1
        else:
            dat = csv.reader(file1, dialect=csv.excel_tab, delimiter=',')
            next(dat, None)
            idx = 0


        filterTwitter = set()
        for line in dat:
                filterTwitter.add(line[idx])

        resultString = ''
        for tic in filterTwitter:
            resultString = resultString+',$'+tic

        return resultString
