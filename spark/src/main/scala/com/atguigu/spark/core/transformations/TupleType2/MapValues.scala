package com.atguigu.spark.core.transformations.TupleType2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: MapValues
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1115:20
  */
object MapValues {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[(Int, String)] = sc.makeRDD(Array((1, "a"), (10, "b"), (11, "c"), (4, "d"), (20, "d"), (10, "e")))

    val result: RDD[(Int, (String, Int))] = value.mapValues(s => (s, 1))
    result.collect().foreach(println)

    sc.stop()
  }

}
