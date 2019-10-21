package com.atguigu.bigdata.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark24_RDD_Action2 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val rdd1 = sc.makeRDD(Array(1,2,3,4), 2)

    // aggregate 行动算子的初始值在分区内计算有效，在分区间计算依然有效
    //val i: Int = rdd1.aggregate(10)(_+_,_+_)
    // println(rdd1.fold(10)(_ + _))
    // TODO 9. 释放连接
    sc.stop()
  }
}
