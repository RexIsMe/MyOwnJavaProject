package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object Spark15_RDD_Transform12 {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val rdd  = sc.makeRDD(List(("a",1), ("b",2), ("c", 3)), 3)

    println(rdd.mapPartitionsWithIndex(
      (index, datas) => {
        datas.map(data => {
          (index, data)
        })
      }
    ).collect().mkString(","))

    // 使用分区器将数据重新分区
    // Spark默认会提供HashPartitioner
    println("*********************************")
    val rdd1: RDD[(String, Int)] = rdd.partitionBy( new MyPartitioner(3) )
    println(rdd1.mapPartitionsWithIndex(
      (index, datas) => {
        datas.map(data => {
          (index, data)
        })
      }
    ).collect().mkString(","))

    // TODO 9. 释放连接
    sc.stop()
  }
}
// 自定义分区器
// 1. 继承Partitioner
// 2. 重写方法
class MyPartitioner(partitions: Int) extends Partitioner {
  override def numPartitions: Int = {
    partitions
  }

  override def getPartition(key: Any): Int = 1
}
