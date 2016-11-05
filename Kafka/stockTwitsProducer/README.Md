###Stock Tweets Producer

* Create a topic named : stockTwitsStream
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic stockTwitsStream
sud@sud-pc:~/kafka_2.11-0.10.0.0$ bin/kafka-topics.sh --list --zookeeper localhost:2181
stockTwitsStream
twitterStream
sud@sud-pc:~/kafka_2.11-0.10.0.0$ 
```
* run stockTwitsALP as :
```
python stockTwitsAPI
```
Currently res in getTweets method is displaying:
```
{u'errors': [{u'message': u'Rate limit exceeded. Client may not make more than 200 requests an hour.'}], u'response': {u'status': 429}}
```

