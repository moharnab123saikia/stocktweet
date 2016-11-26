import com.datastax.spark.connector.SomeColumns
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.kafka.KafkaUtils
import org.joda.time.DateTime
import org.json4s.jackson.JsonMethods._
import com.datastax.spark.connector._
import com.datastax.spark.connector.streaming._
import com.datastax.spark.connector.writer._

/**
  * Created by sud on 11/16/16.
  */
object TestKafkaConsumer {


  val patternWord = "\\W|\\s|\\d"
  val patternTicker = "\\$[A-Z]+".r


  // define positive and negative dictionaries

  val positive = Set(
    "upgrade",
    "upgraded",
    "long",
    "buy",
    "buying",
    "growth",
    "good",
    "gained",
    "well",
    "great",
    "nice",
    "top",
    "support",
    "update",
    "strong",
    "bullish",
    "bull",
    "highs",
    "win",
    "positive",
    "profits",
    "bonus",
    "potential",
    "success",
    "winner",
    "winning",
    "good")


  val negative = Set(
    "downgraded",
    "bears",
    "bear",
    "bearish",
    "volatile",
    "short",
    "sell",
    "selling",
    "forget",
    "down",
    "resistance",
    "sold",
    "sellers",
    "negative",
    "selling",
    "blowout",
    "losses",
    "war",
    "lost",
    "loser")


  // get sentiment of a word
  def getWordSentiment(word: String) = {
    if (positive.contains(word)) 1
    else if (negative.contains(word)) -1
    else 0
  }


  // get index of a month
  def getMonth(month: String) = {
    val m = month.toUpperCase() match {
      case "JAN" => 1
      case "FEB" => 2
      case "MAR" => 3
      case "APR" => 4
      case "MAY" => 5
      case "JUN" => 6
      case "JUL" => 7
      case "AUG" => 8
      case "SEP" => 9
      case "OCT" => 10
      case "NOV" => 11
      case "DEC" => 12

    }
    m.toString
  }


  // return date-time from string
  def getTime(dateString: String) = {
    val str = dateString.split(' ')
    val Month = getMonth(str(1))
    val Day = str(2)
    val Year = str(5).split('"')(0)
    val time = str(3).split(':')
    val Hr = time(0)
    val Min = time(1)
    val Sec = time(2)

    (Year, Month, Day, Hr, Min, Sec)
  }

  // return week index
  def getWeek(year: String, month: String, day: String) = {
    val date = new DateTime(year.toInt, month.toInt, day.toInt, 12, 0, 0, 0)
    date.getWeekyear().toString + '-' + date.getWeekOfWeekyear().toString
  }

  // update function for updateStateByKey
  def updateFunc(values: Seq[(Int, Int)], runningCount: Option[(Int, Int)]):
  Option[(Int, Int)] = {
    val newCount1 = values.map(x => x._1).sum
    val newCount2 = values.map(x => x._2).sum

    val (oldCount1, oldCount2) = runningCount.getOrElse((0, 0))
    Some((newCount1 + oldCount1, newCount2 + oldCount2))
  }


  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    getResult("DAY")


  }


  def getResult(granularity: String) = {

    try {
      val conf = new SparkConf().
                    setMaster("spark://ec2-54-204-101-118.compute-1.amazonaws.com:7077")
//                    setMaster("local[*]")
                    .setAppName("TestKafkaConsumer")
                    .set("spark.driver.allowMultipleContexts", "true")
                    .set("spark.shuffle.blockTransferService", "nio")
//              val conf  = new SparkConf(true)
//                    .setAppName("TestKafkaConsumer")
//                    .set("spark.cassandra.connection.host", "127.0.0.1")
//                    .setMaster("local")
      //      val conf  = new SparkConf(true)
      //        .setAppName("TestKafkaConsumer")
      //        .set("spark.cassandra.connection.host", "127.0.0.1")
      //        .setMaster("local")

      val ssc = new StreamingContext(conf, Seconds(15))

      ssc.checkpoint("twitter count")

      // Set up the input DStream to read from Kafka (in parallel)
      val host = "localhost:2181"
      val group = "SparkStreaming"
      val inputTopic = "twitterStream"
      val topicMap = Map(inputTopic -> 1)
      val kafkaStream = KafkaUtils.createStream(ssc, host, group, topicMap).map(_._2)

      //      val kafkaStream = KafkaUtils.createStream(ssc, "localhost:2181","spark-streaming-consumer-group", Map("twitterStream" -> 5))
      //      kafkaStream.print()

      val tweets = kafkaStream.map(x => parse(x))
       tweets.print()

      val date = tweets.map(x => (getTime(compact(render(x \ "created_at"))), compact(render(x \ "text"))))
        .map { case ((a, b, c, d, e, f), text) => (a, b, c, d, e, f, text) }


      // get texts
      val texts = tweets.map(x => compact(render(x \ "text")))

      // texts.print()

      // map based on time granularity

      val timeStep = granularity match {
        case "YEAR" =>
          date map { case (a, b, c, d, e, f, text) => (a, text) }
        case "MONTH" =>
          date map { case (a, b, c, d, e, f, text) => (a + '-' + b, text) }
        case "WEEK" =>
          date map { case (a, b, c, d, e, f, text) => (getWeek(a, b, c), text) }
        case "DAY" =>
          date map { case (a, b, c, d, e, f, text) => (a + '-' + b + '-' + c, text) }
        case "HR" =>
          date map { case (a, b, c, d, e, f, text) => (a + '-' + b + '-' + c + '-' + d, text) }
        case "MIN" =>
          date map { case (a, b, c, d, e, f, text) => (a + '-' + b + '-' + c + '-' + d + '-' + e, text) }
        case "SEC" =>
          date map { case (a, b, c, d, e, f, text) => (a + '-' + b + '-' + c + '-' + d + '-' + e + '-' + f, text) }
      }

      // timeStep.print()

      // ticker frequency
      val mapResult = timeStep flatMap { case (a, b) => (patternTicker findAllIn b).toList.map(l => ((a, l), 1)) }

      // mapResult.print()

      val words = timeStep flatMap { case (a, b) => (b.trim().toLowerCase().split(patternWord)).map(c => ((a, b), getWordSentiment(c))) }

      val sentiment = words.reduceByKey(_ + _)
      val tickerSentiment = sentiment.flatMap { case ((a, b), c) => (patternTicker findAllIn b).toList.map(l => ((a, l), c)) }

      // join ticker frequency and sentiment
      val pair1 = mapResult join tickerSentiment

      // main operation, update state by Key-time granularity
      val resultPair = pair1.updateStateByKey[(Int, Int)](updateFunc _)

      // flatten the Dstream
      val result = resultPair.map { case ((date, ticker), (frequency, sentiment)) => (date, frequency, ticker.split('$')(1), sentiment) }


      //        result.print();

      val keySpace = "twitterseries"
      //
      val resultDay = result.map { case (date, frequency, ticker, sentiment) => (date.split('-')(0).toInt, date.split('-')(1).toInt, date.split('-')(2).toInt, frequency, ticker, sentiment) }
      resultDay.print();
      //      val sc = new SparkContext(conf)
      //
      //      val collection = sc.parallelize(Seq((2016,11,18,6,"GOOG", 3), (2016,11,19,6,"AMA", 5)))
      //      collection.saveToCassandra("twitterseries", "trendingday", SomeColumns("year", "month", "day", "frequency", "ticker", "sentiment"))
      resultDay.saveToCassandra(keySpace,
                "trendingday",
              SomeColumns("year", "month", "day", "frequency", "ticker", "sentiment"), writeConf = WriteConf(ttl = TTLOption.constant(604800)))


      //        granularity match
      //      {
      //
      //        case "DAY" =>
      //          val resultDay = result.map{case(date, frequency, ticker, sentiment)=> (date.split('-')(0).toInt, date.split('-')(1).toInt, date.split('-')(2).toInt, frequency, ticker, sentiment)}
      //          resultDay.print()
      //          resultDay.saveToCassandra(keySpace, "trendingday", SomeColumns("year", "month", "day", "frequency", "ticker", "sentiment"))
      //
      //      }

      //sentiment.print()
      //tickerSentiment.print()

      ssc.start()
      ssc.awaitTermination()


    }
    catch {
      case unknown => println("An error occured" + unknown)
    }

  }
}
