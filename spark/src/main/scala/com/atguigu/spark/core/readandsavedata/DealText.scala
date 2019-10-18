package com.atguigu.spark.core.readandsavedata

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: DealText
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1117:56
  */
object DealText {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("text").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[String] = sc.textFile("input/1.txt")
    val value2: RDD[(String, Int)] = value.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ +_)

    value2.cache()
    value2.collect().foreach(println)

    value2.saveAsTextFile("output/1.txt")

    sc.stop()
  }

}
