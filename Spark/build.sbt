
name := "Spark"

version := "1.0"

scalaVersion := "2.11.5"
val sparkVersion = "2.0.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0-M3",
  "com.github.nscala-time" %% "nscala-time" % "2.14.0",
  "org.apache.hadoop" % "hadoop-aws" % "2.7.1",
  "joda-time" % "joda-time" % "2.9.6"
).map(
  _.excludeAll(ExclusionRule(organization = "org.mortbay.jetty"))
)


mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}