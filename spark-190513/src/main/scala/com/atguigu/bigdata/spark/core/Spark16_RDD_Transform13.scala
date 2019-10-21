package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object Spark16_RDD_Transform13 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val rdd  = sc.makeRDD(List(("a",1), ("b",2), ("c", 3), ("a",1), ("b",2), ("c", 3)))

    // 2. WordCount
    val reduceByKeyRDD: RDD[(String, Int)] = rdd.reduceByKey((x,y)=>{x+y})

    println(reduceByKeyRDD.collect().mkString(","))


    // TODO 9. 释放连接
    sc.stop()
  }
}
