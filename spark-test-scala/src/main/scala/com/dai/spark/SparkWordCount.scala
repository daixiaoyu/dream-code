package com.dai.spark

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object SparkWordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .getOrCreate()

    val sc: SparkContext = spark.sparkContext

    val data = Array(1, 2, 3, 4, 5)


    val rdd1 = sc.parallelize(data)
    val rdd2 = rdd1.map(s => (s, 1))

    val resultRdd = rdd2.reduceByKey((a, b) => a + b)


    resultRdd.cache()

    resultRdd.foreach(r => println(r))
  }
}
