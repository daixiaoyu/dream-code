package com.dai.spark

import org.apache.spark.sql.SparkSession

object SparkSaveTable {

  val table = "exercise_db.cookie"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .enableHiveSupport()
      .getOrCreate()




  }

}
