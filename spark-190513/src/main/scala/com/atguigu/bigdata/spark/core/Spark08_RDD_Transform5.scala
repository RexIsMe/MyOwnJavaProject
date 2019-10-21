package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark08_RDD_Transform5 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val numRDD: RDD[Int] = sc.makeRDD(Array(10,20,30,40,50,60,70,80), 4)

    // TODO 4. 获取每个分区中的最大值后求和
    //val array = Array(  List(10,20),List(30,40),List(50,60),List(70,80))
    //val ints: Array[Int] = array.map(list=>list.max)
    //println(ints.sum)

    // Array(  List(10,20),List(30,40),List(50,60),List(70,80))
    // Array(20,40,60,80).sum

    val glomRDD: RDD[Array[Int]] = numRDD.glom()
    val partitionMaxRDD: RDD[Int] = glomRDD.map(array=>array.max)
    println(partitionMaxRDD.sum().toInt)


    // TODO 9. 释放连接
    sc.stop()


  }
}
