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
object SortBy {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(1 to 5, 5)

    val ints: Array[Int] = value.sortBy(x => x, false).collect()
    ints.foreach(println)

    ints.sortBy(x => x)(Ordering.Int).foreach(println)
    ints.sortBy(x => x)(Ordering.Int.reverse).foreach(println)

    sc.stop()
  }

}
