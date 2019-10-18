package com.atguigu.spark.core.common

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: NewRDD
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/9/289:51
  */
object NewRDD {

  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)


//    val value: RDD[Int] = sc.parallelize(List(1,2,3,4))


    val value: RDD[Int] = sc.makeRDD(List(1,2,3,4), 5)
    value.saveAsTextFile("output")

    //9.关闭连接
    sc.stop()
  }

}
