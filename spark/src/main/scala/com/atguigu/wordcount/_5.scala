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
object _5{

  def main(args: Array[String]): Unit = {

    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)

    val input = sc.parallelize(Array(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)),2)
    val value: RDD[(String, (Int, Int))] = input.combineByKey((_, 1), (acc:(Int, Int), v) => (acc._1 + v, acc._2 + 1), (acc1:(Int, Int), acc2: (Int, Int))=> (acc1._1 + acc2._1, acc1._2 + acc2._2))
    val collect: Array[(String, Double)] = value.map(t => (t._1, t._2._1.toDouble / t._2._2)).collect
    
    println(collect.mkString)

    sc.stop()
  }



}
