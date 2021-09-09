package com.dai.spark.test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object CacheTest {
  Logger.getRootLogger.setLevel(Level.ERROR)
  def main(args: Array[String]): Unit = {

    // 初始化编程入口
    val sparkConf = new SparkConf().setMaster("local[1]").setAppName("cacheTest")
    val sc = new SparkContext(sparkConf)
    val data = List("dai hello", "dai best")
    val rdd = sc.makeRDD(data)
    val wordRdd = rdd.flatMap(r => r.split(" "))
    // count rdd
    val countRdd = wordRdd.map(r => {
      println(r)
      (r,1)
    })
    countRdd.cache()
    countRdd.checkpoint()

    val resultRdd = countRdd.reduceByKey(_ + _)

    println(resultRdd.toDebugString)

    resultRdd.foreachPartition(iterator=>{
      println("-------------------------")
      iterator.foreach(println(_))
    })

    val result2Rdd = countRdd.groupByKey()

    println(result2Rdd.toDebugString)

    result2Rdd.foreachPartition(iterator=>{
      println("-------------------------")
      iterator.foreach(println(_))
    })


  }
}

