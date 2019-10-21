package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark05_RDD_Transform2 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark02_RDD2").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val numRDD: RDD[Int] = sc.makeRDD(List(1,2,3,4),2)

    // 将RDD通过算子进行转换，变成新的RDD
    // 转换算子 - mapPartitions

    // mapPartitions以分区单位进行逻辑运算，但是运算过程中，数据不会被释放掉，所以容易产生内存溢出
    // 在这个场合下，一般就采用map算子，保证程序执行通过。
    //val mapPartitionsRDD: RDD[Int] = numRDD.mapPartitions(list=>{println(list);list})
    // mapPartitionsRDD.collect().foreach(println)

    // TODO 9. 释放连接
    sc.stop()


  }
}
