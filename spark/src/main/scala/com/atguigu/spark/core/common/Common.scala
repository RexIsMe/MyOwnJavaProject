package com.atguigu.spark.core.common

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: Common
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/9/2814:37
  */
object Common {

  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)

    var list = List(("a", 1),("b", 1),("a", 1), ("c", 1), ("d", 1), ("e", 1),("f", 1),("g", 1), ("h", 1), ("i", 1),
      ("a", 1),("b", 1),("a", 1), ("c", 1), ("a", 1), ("a", 1),("b", 1),("a", 1), ("c", 1), ("a", 1))
    val value: RDD[(String, Int)] = sc.makeRDD(list,4)
    println(value.partitions.length)
    val value2: RDD[Array[(String, Int)]] = value.glom()
    val array: Array[Array[(String, Int)]] = value2.collect()
    array.map(x => {
      println("=================")
      x.toList.foreach(println)
    })



    val value1: RDD[(String, Int)] = value.reduceByKey(_+_)
    println(value1.partitions.length)
    val value3: RDD[Array[(String, Int)]] = value1.glom()
    value3.collect().map(x => {
      println("#####################")
      x.toList.foreach(println)
    })



    //9.关闭连接
    sc.stop()
  }

}
