name := "TestScala"

version := "1.0"

scalaVersion := "2.11.5"

val sparkVersion = "2.0.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-8" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0-M3",
  "org.apache.hadoop" % "hadoop-aws" % "2.7.1",
  "com.google.protobuf" % "protobuf-java" % "2.6.1",
  "joda-time" % "joda-time" % "2.9.6"
).map(
  _.excludeAll(ExclusionRule(organization = "org.mortbay.jetty"))
)