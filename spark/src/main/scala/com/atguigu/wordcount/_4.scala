package com.atguigu.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: _4
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/88:10
  */
object _4 {

  def main(args: Array[String]): Unit = {

    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(Array(("a",3), ("a",2), ("c",4), ("b",3), ("c",6), ("c",8)))
    val collect: Array[(String, Int)] = rdd.foldByKey(0)(_ + _).collect

    println(collect.mkString)

    sc.stop()
  }

}
