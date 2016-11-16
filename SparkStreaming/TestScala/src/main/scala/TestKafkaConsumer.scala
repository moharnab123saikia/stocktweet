import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.kafka.KafkaUtils
import org.json4s.jackson.JsonMethods._
/**
  * Created by sud on 11/16/16.
  */
object TestKafkaConsumer {

  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    try{
      val conf = new SparkConf().setMaster("local[*]").setAppName("TestKafkaConsumer")
//      val confSparkCassandra  = new SparkConf(true)
//        .setAppName("TwitterGetCount")
//        .set("spark.cassandra.connection.host", "127.0.0.1")
//        .setMaster("local")
      val ssc = new StreamingContext(conf, Seconds(60))
      // Set up the input DStream to read from Kafka (in parallel)
      val host = "localhost:2181"
      val group  = "SparkStreaming"
      val inputTopic = "twitterStream"
      val topicMap =  Map(inputTopic -> 1)
      val kafkaStream = KafkaUtils.createStream(ssc, host, group, topicMap).map(_._2)

//      val kafkaStream = KafkaUtils.createStream(ssc, "localhost:2181","spark-streaming-consumer-group", Map("twitterStream" -> 5))
      kafkaStream.print()

      val tweets = kafkaStream.map( x=> parse(x))
      tweets.print()
      ssc.start()
      ssc.awaitTermination()


    }
    catch {
      case unknown => println("An error occured" + unknown)
    }
  }

}
