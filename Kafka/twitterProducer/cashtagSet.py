# -*- coding: utf-8 -*-

import csv


def cashtagSet(Type):
        
    # map the inputs to the function blocks

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
        
#    options = {"DOW30" : DOW30,
#               "NASDAQ100" : NASDAQ100,
#               "NYSE100" : NYSE100,
#               "SP500" : SP500,
#               "NASDAQ_COMPOSITE" : NASDAQ_COMPOSITE,
#               "NYSE_COMPOSITE" : NYSE_COMPOSITE,
#               "ALL" : ALL
#               }


 #   options.get(Type,errhandler)()
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
                filterTwitter.add('$'+line[idx])
        
        return filterTwitter
            

