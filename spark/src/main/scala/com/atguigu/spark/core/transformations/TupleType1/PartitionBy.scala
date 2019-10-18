package com.atguigu.spark.core.transformations.TupleType1

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * @title: PartitionBy
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1015:45
  */
object GroupByAndSoOn {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[(Int, String)] = sc.makeRDD(List((1,"a"), (2,"b"),(3,"c"),(4,"d"),(5,"e")))

    println(value.partitions.length)

    println(value.partitionBy(new HashPartitioner(3)).partitions.length)


    sc.stop()
  }

}
