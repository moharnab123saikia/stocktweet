/**
  * Created by sud on 11/12/16.
  */

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkContext
import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
object TestCassandra {

  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    try{
      val conf  = new SparkConf(true)
        .setAppName("Test")
        .set("spark.cassandra.connection.host", "127.0.0.1")
        .setMaster("local")
//      val conf = new SparkConf().setMaster("local[*]").setAppName("Test")

      val sc = new SparkContext(conf)
      val connector = CassandraConnector(sc.getConf)
      val rdd = sc.cassandraTable("test", "kv")
      println(rdd.count)
      println(rdd.first)
      println(rdd.map(_.getInt("value")).sum)
      println("-----------------")
      println("inserting to DB")
//      val collection = sc.parallelize(Seq(("key5", 5), ("key6", 6)))
//      collection.saveToCassandra("test", "kv", SomeColumns("key", "value"))
//
//      val collection = sc.parallelize(Seq((2016,11,18,6,"GOOG", 3), (2016,11,19,6,"AMA", 5)))
//      collection.saveToCassandra("twitterseries", "trendingday", SomeColumns("year", "month", "day", "frequency", "ticker", "sentiment"))

//            connector.withSessionDo { session =>
//        session.execute("CREATE KEYSPACE test2 WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
//        session.execute("CREATE TABLE test2.words (word text PRIMARY KEY, count int)")
//      }
      println("----------------------------------")
      sc.stop()

    }
    catch {
      case unknown => println("An error occured" + unknown)
    }
  }
}
