/**
  * Created by sud on 11/12/16.
  */

import com.datastax.spark.connector._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkContext
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.Logging
import org.apache.log4j.{Level, Logger}
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._
import org.joda.time._
import org.joda.time.DateTime
import org.joda.time.format._
import java.io.PrintWriter
import com.datastax.spark.connector._
import com.datastax.spark.connector.streaming._
object TestCassandra {

  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    try{
      val scConf  = new SparkConf(true)
        .setAppName("Test")
        .set("spark.cassandra.connection.host", "127.0.0.1")
        .setMaster("local")

      val sc = new SparkContext(scConf)
      val rdd = sc.cassandraTable("test", "kv")
      println(rdd.count)
      println(rdd.first)
      println(rdd.map(_.getInt("value")).sum)
    }
    catch {
      case unknown => println("An error occured" + unknown)
    }
  }
}
