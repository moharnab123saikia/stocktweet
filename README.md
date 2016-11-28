# stocktweet
Data Intensive Computing CSC-591

Version numbers:

1. Scala: 2.12.0 </br>
2. Spark: 2.0.2  </br>
3. Kafka:        </br>

Update the field - native_transport_port: <port-number> Cassandra.yaml file

* Create cluster with 5 slaves
```
./spark-ec2 --key-pair=awskey -s 5 --instance-type=m4.large --spot-price=0.02  --spark-version="2.0.2" --ebs-vol-size=40 --identity-file=awskey.pem --region=us-east-1 --zone=us-east-1b launch spark_cluster
```
* coonect to master node with IP
```
ssh -i awskey.pem ec2-user@54.144.220.136
```
* Do sbt assembly
```
sbt assembly
```
* Transfer files to master node
```
scp -i awskey.pem /home/sud/Desktop/DIC/stocktweet/SparkStreaming/TestScala/target/scala-2.11/testscala_2.11-1.0.jar ec2-user@54.144.220.136:/home/ec2-user/

#TestScala-assembly-1.0.jar

scp -i awskey.pem /home/sud/Desktop/DIC/stocktweet/SparkStreaming/TestScala/target/scala-2.11/TestScala-assembly-1.0.jar ec2-user@54.144.220.136:/home/ec2-user/
```
* connect to EC2
````
ssh -i awskey.pem ec2-user@ec2-54-147-248-95.compute-1.amazonaws.com
```

* Submit app:
```
/root/spark/bin/spark-submit --packages datastax:spark-cassandra-connector:2.0.0-M2-s_2.11 --class TestKafkaConsumer /home/ec2-user/testscala_2.11-1.0.jar

# after assembly
/root/spark/bin/spark-submit --packages datastax:spark-cassandra-connector:2.0.0-M2-s_2.11 --class TestKafkaConsumer /home/ec2-user/TestScala-assembly-1.0.jar
```
* copy rsa.pub to all slaves - not necessary
```
scp -i awskey.pem id_rsa.pub ec2-user@52.90.0.145:/home/ec2-user/
cat id_rsa.pub > ~/.ssh/authorized_keys

scp -i awskey.pem id_rsa.pub ec2-user@54.147.21.169:/home/ec2-user/
ssh -i awskey.pem ec2-user@ec2-54-147-21-169.compute-1.amazonaws.com

scp -i awskey.pem id_rsa.pub ec2-user@54.159.82.247:/home/ec2-user/
ssh -i awskey.pem ec2-user@54.159.82.247

scp -i awskey.pem id_rsa.pub ec2-user@54.163.206.25:/home/ec2-user/
ssh -i awskey.pem ec2-user@54.163.206.25

scp -i awskey.pem id_rsa.pub ec2-user@54.197.3.38:/home/ec2-user/
ssh -i awskey.pem ec2-user@54.197.3.38
```
* stop cluster
```
./spark-ec2 stop test-spark-cluster
```

* start cluster
```
./spark-ec2 start test-spark-cluster
```
* cqlsh
```
cqlsh 172.31.8.2 9042

```
* force start cassandra
```
/usr/sbin/cassandra -f 
```
