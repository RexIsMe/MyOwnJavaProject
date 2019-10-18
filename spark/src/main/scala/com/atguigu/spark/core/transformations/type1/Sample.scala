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
object Sample {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(1 to 10)
    value.sample(true, 0.5).collect().foreach(x => println("11:" + x))
    value.sample(false, 0.5).collect().foreach(x => println("111:" + x))
    value.sample(true, 0.5, 1).collect().foreach(x => println("12:" + x))
    value.sample(true, 0.5, 2).collect().foreach(x => println("13:" + x))
//    value.sample(false, 0.5).collect().foreach(x => println("2:" + x))
//    value.sample(false, 0.5).collect().foreach(x => println("2:" + x))


    sc.stop()

  }

}
