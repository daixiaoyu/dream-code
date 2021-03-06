package com.dai.spark.transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Auther: 马中华 奈学教育 https://blog.csdn.net/zhongqi2513
 * @Date: 2020/6/17 11:53
 * @Description: Computes the cartesian product between two RDDs (i.e. Each item of the first RDD is joined with
 *              each item of the second RDD) and returns them as a new RDD.
 *              (Warning: Be careful when using this function.! Memory consumption can quickly become an issue!)
 **/
object RDD_22_cartesian {
    
    def main(args: Array[String]): Unit = {
    
        // 初始化编程入口
        val sparkConf = new SparkConf().setMaster("local").setAppName("RDD_Test")
        val sc = new SparkContext(sparkConf)
        
        val rdd1 = sc.parallelize(List(1, 2, 3, 4, 5))
        val rdd2 = sc.parallelize(List(6, 7, 8, 9, 10))
        
        val resultRDD: RDD[(Int, Int)] = rdd1.cartesian(rdd2)
        
        resultRDD.foreach(println)
        
    }
    
    def cartesian(sc: SparkContext): Unit = {
        val list1 = List("A", "B")
        val list2 = List(1, 2, 3)
        val list1RDD = sc.parallelize(list1)
        val list2RDD = sc.parallelize(list2)
        list1RDD.cartesian(list2RDD).foreach(t => println(t._1 + "->" + t._2))
    }
}
