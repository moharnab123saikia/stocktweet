#stockTweetsAPI
from kafka import *
import sys
sys.path.insert(0, '../twitterProducer')
from cashtagSet import cashtagSet
import json
import requests
import datetime
import threading


# kafka setup
mykafka = KafkaClient("localhost:9092")
producer = SimpleProducer(mykafka)
topicName = "stockTwitsStream"

def stream_symbol(symbol):        
    url = "https://api.stocktwits.com/api/2/streams/symbol/" + str(symbol) + ".json"
    print url
    try:
        print(requests.get(url))
        content = requests.get(url).text
        #content = json.loads(url)
        print(content)
    except Exception as e:
        print e
	retVal = []
	return retVal

    return json.loads(content)

def getTweets(stocks):
    result = []
    #print(stocks)
    for stock in stocks:
        res = stream_symbol(stock)
        print(res)
        if len(res)==0:
            continue
        else:
            if res['response']['status']==200:
                producer.send_messages(topicName, json.dumps(res['messages']))
                result+= res['messages']
            else:
                continue
        break
    return result


def fetchAndSend():
    
    stocks = cashtagSet("NYSE100")
    '''
    print(stocks)
    set(['$GD', '$GE', '$BHI', '$GM', '$GS', '$WFC', '$NKE', '$HD', '$KMB', '$JPM', '$TRV', '$CVS', '$CVX', '$IBM', '$GIS', '$UNH', '$YUM', '$MRK', '$PRU', '$CAT', '$NOV', '$UNP', '$SPG', '$DUK', '$MON', '$HPQ', '$LMT', '$ABT', '$UTX', '$ACN', '$VZ', '$LLY', '$MDT', '$SCCO', '$BAC', '$PX', '$PG', '$PM', '$BAX', '$CL', '$MMM', '$EXC', '$PFE', '$LOW', '$EMC', '$F', '$PNC', '$D', '$C', '$SO', '$GLW', '$DOW', '$V', '$EMR', '$T', '$DE', '$DD', '$DVN', '$MCD', '$BK', '$PEP', '$BA', '$MET', '$HON', '$AIG', '$USB', '$XOM', '$LVS', '$KO', '$JNJ', '$MA', '$DIS', '$HAL', '$FCX', '$SLB', '$UPS', '$EOG', '$APA', '$APC', '$DHR', '$BMY', '$AXP', '$AGN', '$OXY', '$COP', '$COV', '$TGT', '$NEM', '$TWX', '$ITW', '$WMT', '$BEN', '$MO', '$FDX', '$MS'])
	'''

    getTweets(stocks)
    
    stocks = cashtagSet("NASDAQ100")
    getTweets(stocks)
    
    stocks = cashtagSet("COMPANIES")
    getTweets(stocks)
    #producer.send_messages(topicName, json.dumps(result))


def doWork():
    print datetime.datetime.now()
    fetchAndSend()
    threading.Timer(3600, doWork).start()

if __name__ == "__main__":
    doWork()
    

