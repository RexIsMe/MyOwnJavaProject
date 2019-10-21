package com.atguigu.bigdata.spark.core

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark37_RDD_Sum {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark35_RDD_Hbase").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[(Int, Int)] = sc.makeRDD(List((1,1),(1,2),(1,3),(1,4)))

    //rdd.reduceByKey(_+_).collect().foreach(println)
    var sum = 0
    rdd.foreach{
      case ( k, v ) => {
        sum = sum + v
      }
    }

    println("sum = " + sum)

    // TODO 9. 释放连接
    sc.stop()
  }
}
