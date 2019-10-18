package com.atguigu.spark.core.AdvanceVar


import java.util

import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object RDD_Accumu2 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark35_RDD_Hbase").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[(String, Long)] = sc.makeRDD(List(("xx",1L),("yy",2L),("xx",3L),("yy",4L)))

    // 创建累加器
    val accumu = new MyAccumulator2
    // 注册累加器
    sc.register(accumu, "MyAccumulator")

    rdd.foreach{
      data=> {
        // 使用累加器
        accumu.add(data)
      }
    }

    // 返回累加器的值
    println(accumu.value)



    // TODO 9. 释放连接
    sc.stop()
  }
}
// 自定义累加器
// 1. 继承AccumulatorV2抽象类，并设定泛型In, Out
// 2. 重写方法
class MyAccumulator2 extends AccumulatorV2[(String, Long), util.HashMap[String, Long]] {

  private var wordList = List("xx", "yy")

  // xx -> 1, yy->2, aa->3
  private var wordCount = new util.HashMap[String, Long]()

  // 累加器是否初始化
  override def isZero: Boolean = {
    wordCount.size == 0
  }

  //  复制新的累加器


  // 重置累加器
  override def reset(): Unit = {
    wordCount.clear()
  }

  // 累加计算
  override def add(t: (String, Long)): Unit = {
    if (wordList.contains(t._1)) {
      val value: Long = wordCount.getOrDefault(t._1, 0)
      wordCount.put(t._1, value + t._2)
    }
  }

  override def copy(): AccumulatorV2[(String, Long), util.HashMap[String, Long]] = {
    new MyAccumulator2
  }

  override def merge(other: AccumulatorV2[(String, Long), util.HashMap[String, Long]]): Unit = {
    wordList.foreach(s => {
      val val1: Long = wordCount.getOrDefault(s, 0)
      val val2: Long = other.value.getOrDefault(s, 0)
      wordCount.put(s, val1 + val2)
    })
  }

  override def value: util.HashMap[String, Long] = {
    wordCount
  }
}

