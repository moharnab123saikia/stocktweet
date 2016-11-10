from pyspark import SparkConf, SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils
import operator
import numpy as np
import matplotlib.pyplot as plt


# Declaring conf and sc as global variable to use in other functions
conf = SparkConf().setMaster("local[2]").setAppName("Twitter Streaming Series")
sc = SparkContext(conf=conf)

def main():
    ssc = StreamingContext(sc, 60)   # Create a streaming context with batch interval of 10 sec
    ssc.checkpoint("twitter count")
    pwords = Set([
        "upgrade",
        "upgraded",
        "long",
        "buy",
        "buying",
        "growth",
        "good",
        "gained",
        "well",
        "great",
        "nice",
        "top",
        "support",
        "update",
        "strong",
        "bullish",
        "bull",
        "highs",
        "win",
        "positive",
        "profits",
        "bonus",
        "potential",
        "success",
        "winner",
        "winning",
        "good"])


    nwords =Set([
        "downgraded",
        "bears",
        "bear",
        "bearish",
        "volatile",
        "short",
        "sell",
        "selling",
        "forget",
        "down",
        "resistance",
        "sold",
        "sellers",
        "negative",
        "selling",
        "blowout",
        "losses",
        "war",
        "lost",
        "loser"])
       
    counts = stream(ssc, pwords, nwords, 100)
    make_plot(counts)


def fx(word,pwords,nwords):
    if word in pwords:
        return "positive"
    elif word in nwords:
        return "negative"
    else:
        return None

def updateFunction(newValues, runningCount):
    if runningCount is None:
       runningCount = 0
    return sum(newValues, runningCount) 

def stream(ssc, pwords, nwords, duration):
    kstream = KafkaUtils.createDirectStream(
        ssc, topics = ['twitterStream'], kafkaParams = {"metadata.broker.list": 'localhost:2181'})
    tweets = kstream.map(lambda x: x[1].encode("ascii","ignore"))
    words = tweets.flatMap(lambda line: line.split(" "))
    pairs = words.map(lambda word: (fx(word,pwords,nwords), 1)).filter(lambda x: x[0]=="positive" or x[0] == "negative")

    wordCounts = pairs.reduceByKey(lambda x, y: x + y)
    #wordCounts = wordCounts.filter(lambda x: x[0]=="positive" or x[0] == "negative")

    running_counts = pairs.updateStateByKey(updateFunction)

    running_counts.pprint()

# Print the first ten elements of each RDD generated in this DStream to the console

    # Each element of tweets will be the text of a tweet.
    # You need to find the count of all the positive and negative words in these tweets.
    # Keep track of a running total counts and print this at every time step (use the pprint function).
    # YOUR CODE HERE
    
    
    # Let the counts variable hold the word counts for all time steps
    # You will need to use the foreachRDD function.
    # For our implementation, counts looked like:
    #   [[("positive", 100), ("negative", 50)], [("positive", 80), ("negative", 60)], ...]
    counts = []
    wordCounts.foreachRDD(lambda t,rdd: counts.append(rdd.collect()))
    ssc.start()                         # Start the computation
    ssc.awaitTerminationOrTimeout(duration)
    ssc.stop(stopGraceFully=True)

    return counts


if __name__=="__main__":
    main()