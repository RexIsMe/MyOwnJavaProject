package com.atguigu.spark.core.PersistAndCheckPoint

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: CacheDemo
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1117:15
  */
object CacheDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rdd1 = sc.parallelize(Array("ab", "bc"))
    val rdd2 = rdd1.flatMap(x => {
      println("flatMap...")
      x.split("")
    })
    val rdd3: RDD[(String, Int)] = rdd2.map(x => {
      (x, 1)
    })

    rdd3.cache()
    rdd3.collect.foreach(println)
    println("-----------")
    rdd3.collect.foreach(println)

  }

}
