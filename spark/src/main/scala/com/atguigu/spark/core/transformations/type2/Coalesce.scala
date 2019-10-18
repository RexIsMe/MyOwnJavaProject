package com.atguigu.spark.core.transformations.type2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Coalesce
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/109:37
  */
object Coalesce {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(1 to 100, 5)
    println(value.partitioner)

    println(value.partitions.length)


    println(value.coalesce(2).partitions.length)
    println(value.coalesce(10).partitions.length)
    println(value.coalesce(10, true).partitions.length)


    println(value.repartition(20).partitions.length)

    sc.stop()
  }

}
