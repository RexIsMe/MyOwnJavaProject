package com.atguigu.spark.core.common

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: WordCount
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/9/2713:59
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)
    // 3. 使用sc创建RDD并执行相应的transformation和action
    val tuples: Array[(String, Int)] = sc.textFile("input")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
      //      .saveAsTextFile("/result")
      .collect()
    println(tuples.mkString(","))
//    sc.textFile("input")
//      .flatMap(_.split(" "))
//      .map((_, 1))
//      .reduceByKey(_ + _)
//            .saveAsTextFile("result")
    // 4. 关闭连接
    sc.stop()
  }



}
