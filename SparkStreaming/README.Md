README

## Install Scala
* Download binary file from http://scala-lang.org/download
* Move and extract the binary file to $HOME
* Set Environment variables 
```
export SCALA_HOME=$HOME/scala-2.12.0
export PATH="$PATH:$SCALA_HOME/bin"
```
* Run the following :
```
source $HOME/.bashrc
```


```
$SPARK_HOME/bin/spark-submit --jars ./spark-streaming-kafka-assembly_2.10-1.5.2.jar twitterGetCount.py
```

* connector to SPARK-CASSANDRA
```
$SPARK_HOME/bin/spark-shell --conf spark.cassandra.connection.host=127.0.0.1 --packages datastax:spark-cassandra-connector:1.5.0-s_2.10
```

### Project- TestScala
Twitter Stream to Cassandra

* First compile the ddls present in Cassandra-ddl.
* Import project to IntelliJ 
* Dependencies: 
```
              Scala 2.10.1
              Spark 1.5.0
              Java 8
```              
* port change of Cassandra
```
sbt app/run -Dcassandra.connection.host=127.0.1.1


$SPARK_HOME/bin/spark-shell --conf spark.cassandra.connection.host=127.0.1.1 \
                            --packages datastax:spark-cassandra-connector:1.5.0-s_2.10
```
In /etc/cassandra/cassandra.yaml
```
rpc_address: 127.0.1.1
 rpc_interface: eth1
 rpc_interface_prefer_ipv6: false

# port for Thrift to listen for clients on
rpc_port: 9160
```
