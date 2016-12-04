#!/bin/sh

# change directory to Kafka

cd /home/sud/kafka_2.11-0.10.0.0

# stop zookeeper if already running 
sudo service zookeeper stop

# start zookeeper
x-terminal-emulator -e 
nohup bin/zookeeper-server-start.sh config/zookeeper.properties &



# start Kafka
x-terminal-emulator -e 
nohup bin/kafka-server-start.sh config/server.properties &

x-terminal-emulator -x-terminal-emulator
# create three topics
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic twitterStream
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic stockTwitsStream
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic stockData

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic stockStream

# check the created topics
# /home/sud/kafka_2.11-0.10.0.0/
bin/kafka-topics.sh --list --zookeeper localhost:2181

# check data in the topic
./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic twitterStream --from-beginning
./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic stockStream --from-beginning

# open a new tab again
#WID=$(xprop -root | grep "_NET_ACTIVE_WINDOW(WINDOW)"| awk '{print $5}')
#xdotool windowfocus $WID
#xdotool key ctrl+shift+t
#wmctrl -i -a $WID
