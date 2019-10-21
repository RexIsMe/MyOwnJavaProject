package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark17_RDD_Transform14 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val rdd  = sc.makeRDD(List(("a",1), ("b",2), ("c", 3), ("a",1), ("b",2), ("c", 3)))

    // 3. WordCount
    val groupRDD: RDD[(String, Iterable[Int])] = rdd.groupByKey()

    val wordToCountRDD: RDD[(String, Int)] = groupRDD.map {
      case (word, iter) => {
        (word, iter.sum)
      }
    }

    println(wordToCountRDD.collect().mkString(","))


    // TODO 9. 释放连接
    sc.stop()
  }
}
