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
ssh -i awskey.pem ec2-user@54.227.15.252
```
* Do sbt assembly
```
sbt assembly
```
* Transfer files to master node
```
scp -i awskey.pem /home/sud/Desktop/DIC/stocktweet/SparkStreaming/TestScala/target/scala-2.11/testscala_2.11-1.0.jar ec2-user@54.227.15.252:/home/ec2-user/

#TestScala-assembly-1.0.jar
scp -i awskey.pem /home/sud/Desktop/DIC/new/stocktweet/SparkStreaming/TestScala/target/scala-2.11/TestScala-assembly-1.0.jar ec2-user@54.227.15.252:/home/ec2-user/

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
cqlsh 172.31.6.108 9042

```
* force start cassandra
```
/usr/sbin/cassandra -f 
```
* Install Java 8
```
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u112-b15/jdk-8u112-linux-x64.rpm
sudo rpm -ivh jdk-8uversion-linux-x64.rpm
Change Java path
sudo alternatives --install /usr/bin/java java /usr/java/jdk1.8.0_version/bin/java 200000
sudo alternatives --config jav
export JAVA_HOME=/usr/java/latest
export PATH=$JAVA_HOME/bin:$PATH
```
* installing cassandra

https://docs.datastax.com/en/cassandra/3.x/cassandra/install/installRHEL.html

* Installing Cassandra
```
sudo nano /etc/yum.repos.d/datastax.repo
-Add

[datastax-ddc] 
name = DataStax Repo for Apache Cassandra
baseurl = http://rpm.datastax.com/datastax-ddc/3.9
enabled = 1
gpgcheck = 0

sudo yum install datastax-ddc

-Change permission
 sudo chmod 777 /var/lib/cassandra/data
 sudo chmod 777 /var/lib/cassandra/commitlog
 sudo chmod 777 /var/lib/cassandra/saved_caches
 sudo chmod 777 /var/lib/cassandra/hints
-Start Forcefully
/usr/sbin/cassandra -f 
```
* Install Kafka
```
wget http://download.nextag.com/apache/kafka/0.10.1.0/kafka_2.11-0.10.1.0.tgz
tar -zxvf kafka-0.10.1.0-src.tgz
mv kafka-0.10.1.0-src kafka-0.10.1.0
```
* Open all port for master IP.



* Background
nohup /root/spark/bin/spark-submit --packages datastax:spark-cassandra-connector:2.0.0-M2-s_2.11 --class TwitterTopTrending /home/ec2-user/TestScala-assembly-1.0.jar &
nohup bin/zookeeper-server-start.sh config/zookeeper.properties &
nohup bin/kafka-server-start.sh config/server.properties &
nohup python twitterAPI.py &
