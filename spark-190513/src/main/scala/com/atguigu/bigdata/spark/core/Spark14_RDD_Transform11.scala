package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark14_RDD_Transform11 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD

    // 1. WordCount
    val wordRDD: RDD[String] = sc.makeRDD(List("Hello", "Hello", "Hello", "Spark"))

    val wordGroupRDD: RDD[(String, Iterable[String])] = wordRDD.groupBy(word=>word)

    val wordToCountRDD: RDD[(String, Int)] = wordGroupRDD.map {
      case (word, iter) => {
        (word, iter.size)
      }
    }
    wordToCountRDD.collect().foreach(println)
    // TODO 9. 释放连接
    sc.stop()


  }
}
