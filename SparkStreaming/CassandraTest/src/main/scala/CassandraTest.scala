/**
  * Created by sud on 11/19/16.
  */

// Ref - https://github.com/datastax/spark-cassandra-connector/blob/master/doc/0_quick_start.md

/**
  * CREATE KEYSPACE test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };
CREATE TABLE test.kv(key text PRIMARY KEY, value int);
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
      //      val scConf  = new SparkConf(true)
      //        .setAppName("Test")
      //        .set("spark.cassandra.connection.host", "127.0.1.1")
      //        .setMaster("local")
      val conf = new SparkConf().setMaster("local[*]").setAppName("Test")

      val sc = new SparkContext(conf)
      val connector = CassandraConnector(sc.getConf)
      //      val rdd = sc.cassandraTable("test", "kv")
      //      println(rdd.count)
      //      println(rdd.first)
      //      println(rdd.map(_.getInt("value")).sum)
      println("-----------------")
      println("inserting to DB")
            val collection = sc.parallelize(Seq(("key7", 7), ("key8", 8)))
            collection.saveToCassandra("test", "kv", SomeColumns("key", "value"))
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