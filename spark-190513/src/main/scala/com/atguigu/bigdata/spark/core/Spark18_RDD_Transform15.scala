package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark18_RDD_Transform15 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val rdd = sc.parallelize(List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8)),2)

    // 函数柯里化
    // 第一个参数列表中参数含义：初始值
    // 第二个参数列表中
    //       第一个参数含义： seqOp, 分区内计算规则
    //       第二个参数含义： combOp, 分区间计算规则

    // p0 : ("a",3),("a",2),("c",4)
    //    :  ("a",3), ("c",4)
    // p1 : ("b",3),("c",6),("c",8)
    //    : ("b",3), ("c", 8)

    // ("a",3), ("b", 3), ("c"，12)
    /*
    val aggregate: RDD[(String, Int)] = rdd.aggregateByKey(10)(
      (x, y) => math.max(x, y),
      (x, y) => x + y
    )

     */

    // 4. WordCount
    val aggregate: RDD[(String, Int)] = rdd.aggregateByKey(0)(
      (x, y) => x + y,
      (x, y) => x + y
    )

    // 5. WordCount
    // 当aggregateByKey中分区内计算规则和分区间计算规则相同时，可以使用foldByKey进行简化
    val foldRDD: RDD[(String, Int)] = rdd.foldByKey(0)(_+_)

    println(foldRDD.collect().mkString(","))


    // TODO 9. 释放连接
    sc.stop()
  }
}
