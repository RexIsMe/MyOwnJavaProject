package com.atguigu.spark.core.transformations.type1

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: map
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/108:07
  */
object AboutMap {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value1: RDD[Int] = sc.parallelize(1 to 10)
    value1.map(x => {
      println("map")
      x*2
    }).collect().foreach(println)



    val value2: RDD[Int] = sc.makeRDD(1 to 10, 4)
    value2.mapPartitions(a => {
      println("out")
      a.map(x => {
        println("mapParttions")
        x*2
      })
    }).collect().foreach(println)


    val value3: RDD[Int] = sc.makeRDD(1 to 10, 4)
    value3.mapPartitionsWithIndex((index, item) => {
      item.map(x => (index, x*2))
    }).collect().foreach(println)


    val value4: RDD[Int] = sc.makeRDD(1 to 10, 4)
    val result: RDD[Int] = value4.flatMap(x =>  List(x*x, x*x*x))
    result.collect().foreach(println)


    sc.stop()

  }

}
