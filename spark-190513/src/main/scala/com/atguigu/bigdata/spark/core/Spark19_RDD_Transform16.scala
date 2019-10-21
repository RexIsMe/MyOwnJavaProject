package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark19_RDD_Transform16 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val input = sc.parallelize(Array(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)),2)
    // combineByKey 根据Key进行聚合操作
    // 参数列表中可以传递三个参数，三个参数分别都是函数
    //      第一个参数含义：进行计算前对数据的结构进行改变
    //      第二个参数含义：分区内计算规则
    //      第三个参数含义：分区间计算规则

    // 88 => (88) + 91 => (179) => (179) + 95 => (274)
    // 95 => (95,1) + 93 => (188, 2) => (188, 2) + 98 => (286, 3)

    /*
    val combineByKeyRDD: RDD[(String, (Int, Int))] = input.combineByKey(
      num => (num, 1),
      (t: (Int, Int), num) => (t._1 + num, t._2 + 1),
      (t1: (Int, Int), t2: (Int, Int)) => (t1._1 + t2._1, t1._2 + t2._2)
    )
     */
    // 6 WordCount
    val combineByKeyRDD: RDD[(String, Int)] = input.combineByKey(
      num => num,
      (num1: Int, num2: Int) => num1 + num2,
      (num1: Int, num2: Int) => num1 + num2
    )

    println(combineByKeyRDD.collect().mkString(","))

    // TODO 9. 释放连接
    sc.stop()
  }
}
