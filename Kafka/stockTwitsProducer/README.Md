###Stock Tweets Producer

* Create a topic named : stockTwitsStream
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic stockTwitsStream
sud@sud-pc:~/kafka_2.11-0.10.0.0$ bin/kafka-topics.sh --list --zookeeper localhost:2181stockTwitsStream
twitterStream
sud@sud-pc:~/kafka_2.11-0.10.0.0$ 
```


