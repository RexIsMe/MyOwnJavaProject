package com.atguigu.spark.core.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: CommonTest
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1115:31
  */
object CommonTest {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("ct").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    sc.stop()

  }

}
