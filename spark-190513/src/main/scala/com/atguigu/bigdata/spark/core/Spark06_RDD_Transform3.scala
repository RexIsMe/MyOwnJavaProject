package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark06_RDD_Transform3 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark02_RDD2").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val numRDD: RDD[Int] = sc.makeRDD(List(1,3,2,4),2)

    // 循环打印数据，并同时输出数据的分区号
    // 转换算子 - mapPartitionsWithIndex
    val mapPartitionsWithIndexRDD: RDD[(Int, Int)] = numRDD.mapPartitionsWithIndex(
      (index, datas) => {
        datas.map(
          data => (index, data)
        )
      }
    )
    val collect: Array[(Int, Int)] = mapPartitionsWithIndexRDD.collect

    val tuples: Array[(Int, Int)] = collect.filter(t=>t._1 == 1)
    println(tuples.map(_._2).sum)
    // TODO 9. 释放连接
    sc.stop()


  }
}
