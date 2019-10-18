package com.atguigu.spark.core.transformations.type1

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Glom
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/108:30
  */
object Glom {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value1: RDD[Int] = sc.makeRDD(1 to 10, 4)
    val array: Array[Array[Int]] = value1.glom().collect()
    array.map(x => {
      println("============")
      x.foreach(println)
    })

    sc.stop()
  }

}
