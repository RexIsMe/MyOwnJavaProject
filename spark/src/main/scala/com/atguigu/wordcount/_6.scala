package com.atguigu.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: _4
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/88:10
  */
object _6{

  def main(args: Array[String]): Unit = {

    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(Array(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)),2)
    val rdd1= rdd.groupBy{case t=>t._1}
    val rdd2= rdd1.map {
      case (words, t) => {
        (words,t.map {
          case (word,num) => num
        })
      }
    }
    val result: RDD[(String, Int)] = rdd2.map {
      case (word, t) => (word, t.sum)
    }

    println(result.collect.mkString)

    sc.stop()
  }



}
