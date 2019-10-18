package com.atguigu.spark.core.partitioner

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
  * @title: PartitionerDemo
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1117:33
  */
object PartitionerDemo {

  def main(args: Array[String]): Unit = {

    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)


    val value1: RDD[Int] = sc.makeRDD(Array(10))
    val partitioner: Option[Partitioner] = value1.partitioner
    println(partitioner)


    val value2: RDD[(String, Int)] = sc.makeRDD(Array(("hello", 1), ("world", 1)))
    println(value2.partitioner)

    val value3: RDD[(String, Int)] = sc.makeRDD(Array(("hello", 1), ("world", 1)), 5)
    println(value3.partitioner)


    import org.apache.spark.HashPartitioner
    val value4: RDD[(String, Int)] = sc.makeRDD(Array(("hello", 1), ("world", 1)))
    println(value4.partitions.length)
    val value5: RDD[(String, Int)] = value4.partitionBy(new HashPartitioner(3))
    println(value5.partitioner)
    println(value5.partitions.length)

    sc.stop()

  }

}
