package com.atguigu.bigdata.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark23_RDD_Action1 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    //val rdd1 = sc.parallelize(1 to 100)

    // 行动算子 - reduce
    // 所谓的行动算子，就是用于提交Job（ActiveJob对象）
    //val i: Int = rdd1.reduce(_+_)
    //println(i)

    //rdd1.collect().foreach(println)

    //println(rdd1.count())

    //rdd1.take(3).foreach(println)
    //println(rdd1.first())

    val rdd = sc.makeRDD(List(1,4,5,3,2))

    rdd.take(3).foreach(println)
    println("***************")
    rdd.takeOrdered(3).foreach(println)

    // TODO 9. 释放连接
    sc.stop()
  }
}
