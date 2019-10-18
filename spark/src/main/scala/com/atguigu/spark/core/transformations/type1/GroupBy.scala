package com.atguigu.spark.core.transformations.type1

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: GroupBy
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/109:10
  */
object GroupBy {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(1 to 10, 4)
    value.groupBy(x => if(x%2 == 1) "dd" else "bb").collect().foreach(println)
    value.groupBy(x => x%2 == 1).collect().foreach(println)
    value.groupBy(x => x).collect().foreach(println)

    sc.stop()

  }

}
