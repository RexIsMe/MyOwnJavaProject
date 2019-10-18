package com.atguigu.spark.core.transformations.type1

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Filter
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/109:17
  */
object Filter {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(1 to 10)
    val result: RDD[Int] = value.filter(x => x%2 == 1)
    result.collect().foreach(println)

    sc.stop()

  }

}
