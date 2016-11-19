import sbt.ExclusionRule

name := "CassandraTest"

version := "1.0"

scalaVersion := "2.10.1"
val sparkVersion = "1.5.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % sparkVersion,
  "org.apache.spark" % "spark-streaming-kafka_2.10" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.5.0",
  "org.apache.hadoop" % "hadoop-aws" % "2.7.1",
  "com.google.protobuf" % "protobuf-java" % "2.6.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "joda-time" % "joda-time" % "2.8.2"
).map(
  _.excludeAll(ExclusionRule(organization = "org.mortbay.jetty"),
               ExclusionRule(organization = "org.scala-lang"))
)