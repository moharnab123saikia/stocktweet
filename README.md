# stocktweet
Data Intensive Computing CSC-591

Version numbers:

1. Scala: 2.12.0 </br>
2. Spark: 2.0.2  </br>
3. Kafka:        </br>

Update the field - native_transport_port: <port-number> Cassandra.yaml file

* Deploy to AWS
```
./spark-ec2 --key-pair=awskey -s 5 --spark-version="2.0.2" --identity-file=awskey.pem --region=us-east-1 --zone=us-east-1b launch test-spark-cluster

ssh -i awskey.pem ec2-user@ec2-54-147-248-95.compute-1.amazonaws.com
run 'sbt package'

transfer to ec2:
scp -i awskey.pem /home/sud/Desktop/DIC/stocktweet/SparkStreaming/TestScala/target/scala-2.11/testscala_2.11-1.0.jar ec2-user@54.147.248.95:/home/ec2-user/


connect to ec2:
ssh -i awskey.pem ec2-user@ec2-54-147-248-95.compute-1.amazonaws.com

Submit app:

/root/spark/bin/spark-submit --class GenerateNames /home/ec2-user/testscala_2.11-1.0.jar 
```
