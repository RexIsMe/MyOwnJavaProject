package com.atguigu.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: _1
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/87:55
  */
object _2 {

  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)
    // 3. 使用sc创建RDD并执行相应的transformation和action
    val tuples: Array[(String, Int)] = sc.textFile("input")
      .flatMap(_.split(" "))
      .map((_, 1))
      .groupByKey()
      .map(t => (t._1, t._2.sum))
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
