package kz.nearbygems.spark

import org.apache.spark.sql.SparkSession

object Main extends App {


  private case class Language(language: String, users: Long) {
    override def toString: String = s"$language[$users]"
  }

  private val spark: SparkSession = SparkSession.builder()
                                                .master("local")
                                                .appName("nearbygems")
                                                .getOrCreate()

  import spark.implicits._

  private val df = spark.read.format("csv")
                        .option("header", "true")
                        .option("inferSchema", "true")
                        .load("src/main/resources/languages.csv")

  df.show()

  df.as[Language].collect().map(l => l.toString).foreach(println)
}
