package com.atguigu.spark.core.partitioner

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, RangePartitioner, SparkConf, SparkContext}

/**
  * @title: Test
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1117:42
  */
object Test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Practice").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(Array((10, "a"), (20, "b"), (30, "c"), (40, "d"), (50, "e"), (60, "f")))
    // 把分区号取出来, 检查元素的分区情况
    val rdd2: RDD[(Int, String)] = rdd1.mapPartitionsWithIndex((index, it) => it.map(x => (index, x._1 + " : " + x._2)))

    println(rdd2.partitions.length)
    println(rdd2.collect.mkString(","))

    // 把 RDD1使用 HashPartitioner重新分区
    val rdd3 = rdd1.partitionBy(new HashPartitioner(5))
    println(rdd3.partitions.length)
    // 检测RDD3的分区情况
    val rdd4: RDD[(Int, String)] = rdd3.mapPartitionsWithIndex((index, it) => it.map(x => (index, x._1 + " : " + x._2)))
    println(rdd4.collect.mkString(","))

    // 把 RDD1使用 RangPartitioner重新分区
//    val rdd5 = rdd1.partitionBy(new RangePartitioner(5))
//    println(rdd5.partitions.length)
//    // 检测RDD3的分区情况
//    val rdd6: RDD[(Int, String)] = rdd5.mapPartitionsWithIndex((index, it) => it.map(x => (index, x._1 + " : " + x._2)))
//    println(rdd6.collect.mkString(","))


  }
}
