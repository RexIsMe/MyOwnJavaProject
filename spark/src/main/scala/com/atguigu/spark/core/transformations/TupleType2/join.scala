package com.atguigu.spark.core.transformations.TupleType2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: join
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1115:22
  */
object join {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value1: RDD[(Int, String)] = sc.makeRDD(Array((1, "a"), (1, "b"), (2, "c")))
    val value2: RDD[(Int, String)] = sc.makeRDD(Array((1, "aa"), (3, "bb"), (2, "cc")))

    value1.join(value2).collect().foreach(println)

    sc.stop()
  }

}
