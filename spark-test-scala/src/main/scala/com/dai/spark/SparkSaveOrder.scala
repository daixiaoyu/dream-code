package com.dai.spark

import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.dai.base.MathUtil

import java.util


object SparkSaveOrder {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("saveOrder").master("local[4]")
      .enableHiveSupport()
      .getOrCreate()

    val orderList = new util.ArrayList[Row]()

    for (i <- 1 to 99) {

      val day = (i / 10) + 1
      var dayString: String = null
      if (day < 10) {
        dayString = "0" + day
      } else {
        dayString = day.toString
      }

      orderList.add(
        Row(
          i.toLong,
          "用户" + MathUtil.random(6),
          "2021-01-" + dayString,
          "202101" + dayString,
          1L
        ))

    }

    val schema = StructType(
      List(
        StructField("id", LongType, true),
        StructField("name", StringType, true),
        StructField("day", StringType, true),
        StructField("dt", StringType, true),
        StructField("cost", LongType, true)
      ))

    val orderDataFrame = spark.createDataFrame(orderList, schema)

    orderDataFrame.write.mode("overwrite").parquet("hdfs://localhost:9000/user/gump/hive/exercise/order")

  }
}
