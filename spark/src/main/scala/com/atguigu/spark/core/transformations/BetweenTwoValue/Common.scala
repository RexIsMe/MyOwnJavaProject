package com.atguigu.spark.core.transformations.BetweenTwoValue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Common
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1015:28
  */
object Common {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value1: RDD[Int] = sc.makeRDD(1 to 10)
    val value2: RDD[Int] = sc.makeRDD(6 to 15)

    val result1: RDD[Int] = value1.union(value2)
    println("11111111111")
    result1.collect().foreach(println)

    val result2: RDD[Int] = value1.subtract(value2)
    println("2222222222")
    result2.collect().foreach(println)

    val result3: RDD[Int] = value1.intersection(value2)
    println("33333333333")
    result3.collect().foreach(println)

    val result4: RDD[(Int, Int)] = value1.cartesian(value2)
    println("444444444444")
    result4.collect().foreach(println)

    val result5: RDD[(Int, Int)] = value1.zip(value2)
    println("55555555555")
    result5.collect().foreach(println)


    sc.stop()

  }

}
