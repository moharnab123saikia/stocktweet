/**
  * Created by sud on 12/4/16.
  */

import org.joda.time._
import org.joda.time.format._

object TestDateString {

  def isValid(f : String, d : String) = {
//    try {
//      val format = new java.text.SimpleDateFormat(f)
//      format.parse(d)
//    }catch(java.text.ParseException e) {
//      false
//    }
  }
  val format = DateTimeFormat.forPattern("yyyy-MM-dd k:m:s")
  def validate(date: String) = try {
    format.parseMillis(date)
    true
  }
  catch {
    case e: IllegalArgumentException => false
  }
  def main(args : Array[String]) {
    println(validate("2002-10-15 10:55:01"))
    println(validate("2002-10-15 10:55:01"))
    println(validate("2016-12-02 19:46:00"))
    println(validate("S"))
  }

}
