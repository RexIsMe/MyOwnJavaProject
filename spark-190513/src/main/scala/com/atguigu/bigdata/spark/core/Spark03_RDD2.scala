package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark03_RDD2 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark02_RDD2").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // 创建RDD : 从存储系统中创建
    // 路径的位置需要考虑运行环境
    // 读取文件的分区规则是以文件为单位
    // 读取文件的规则是以行为单位
    val lineRDD: RDD[String] = sc.textFile("input/4.txt")
    lineRDD.saveAsTextFile("output")

    // TODO 9. 释放连接
    sc.stop()


  }
}
