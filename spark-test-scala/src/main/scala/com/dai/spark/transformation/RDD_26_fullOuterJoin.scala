package com.dai.spark.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Auther: 马中华 奈学教育 https://blog.csdn.net/zhongqi2513
 * @Date: 2020/6/17 18:35
 * @Description: Performs the full outer join between two paired RDDs.
 **/
object RDD_26_fullOuterJoin {
    
    def main(args: Array[String]): Unit = {
    
        // 初始化编程入口
        val sparkConf = new SparkConf().setMaster("local").setAppName("RDD_Test")
        val sc = new SparkContext(sparkConf)
    
        val pairRDD1 = sc.parallelize(List( ("cat",2), ("cat", 5), ("book", 4),("cat", 12)))
        val pairRDD2 = sc.parallelize(List( ("cat",2), ("cup", 5), ("mouse", 4),("cat", 12)))
        val resultRDD: RDD[(String, (Option[Int], Option[Int]))] = pairRDD1.fullOuterJoin(pairRDD2)
        
        resultRDD.foreach(x => {
            println(x._1, x._2._1.getOrElse("null"), x._2._2.getOrElse("null"))
        })
    }
}
