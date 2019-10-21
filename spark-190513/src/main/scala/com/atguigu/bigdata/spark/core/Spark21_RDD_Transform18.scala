package com.atguigu.bigdata.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark21_RDD_Transform18 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    var rdd1 = sc.parallelize(Array((1, "a"), (1, "b"), (2, "c")))
    var rdd2 = sc.parallelize(Array((1, "aa"), (3, "bb"), (2, "cc")))

    // 转换算子 - join
    //val tuples: Array[(Int, (String, String))] = rdd1.join(rdd2).collect()
    //val tuples: Array[(Int, (String, String))] = rdd1.join(rdd2).collect()

    // 转换算子 cogroup
    val tuples: Array[(Int, (Iterable[String], Iterable[String]))] = rdd1.cogroup(rdd2).collect()

    tuples.foreach(println)

    // TODO 9. 释放连接
    sc.stop()
  }
}
