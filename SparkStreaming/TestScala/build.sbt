name := "TestScala"

version := "1.0"


scalaVersion := "2.10.5"

//libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.2.0-cdh5.3.2" % "provided"


//resolvers += "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

//scalaVersion := "2.12.0"

//libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.1"

//libraryDependencies += "org.apache.spark" %% "spark-core" % "1.2.0" % "provided"
//libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.2.0" % "provided"
//libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.2.0"
//resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
//libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "1.6.0"
//libraryDependencies += "joda-time" % "joda-time" % "2.7"
//libraryDependencies += "joda-time" % "joda-time" % "2.7" withSources()
//libraryDependencies += "org.joda" % "joda-convert" % "1.2" withSources()
//libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0-M3"
//resolvers += "Spark Packages Repo" at "https://dl.bintray.com/spark-packages/maven"
//libraryDependencies += "datastax" % "spark-cassandra-connector" % "1.6.0-s_2.10"
//libraryDependencies += "datastax" % "spark-cassandra-connector" % "1.5.0-s_2.10"
//libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.2.0-alpha1" withSources()
//libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.0.0"
//libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.2.0"

//libraryDependencies ++= Seq(
//  "org.apache.spark" % "spark-sql_2.10" % "1.2.1" excludeAll(
//    ExclusionRule("org.apache.hadoop")
//    ),
//  "org.apache.hadoop" % "hadoop-client" % "2.2.0"
//)


val sparkVersion = "1.5.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % sparkVersion,
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.2.0",
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.5.0",
  "org.apache.hadoop" % "hadoop-aws" % "2.7.1",
  "com.google.protobuf" % "protobuf-java" % "2.6.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "joda-time" % "joda-time" % "2.8.2"
).map(
  _.excludeAll(ExclusionRule(organization = "org.mortbay.jetty"))
)