package com.dai.spark

import org.apache.spark.sql.SparkSession

object SparkOne {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").master("local[*]")
      .enableHiveSupport()
      .getOrCreate()

    //val result = spark.read.text("hdfs://localhost:9000/user/gump/hive/exercise/user")

    val result = spark.sql("select * from exercise_db.exercise_pv")

    result.show()

  }


}
