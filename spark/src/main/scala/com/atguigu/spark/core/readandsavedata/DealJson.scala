package com.atguigu.spark.core.readandsavedata

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: DealJson
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1118:00
  */
object DealJson {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("text").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value1: RDD[String] = sc.textFile("input/1.json")
    value1.collect().foreach(println)

    import scala.util.parsing.json.JSON
    val value2: RDD[Option[Any]] = value1.map(JSON.parseFull)
    value2.collect().foreach(println)


    sc.stop()
  }

}
