package com.atguigu.spark.core.readandsavedata

import java.sql.DriverManager

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: DealText
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1117:56
  */
object DealMysql {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("text").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    //定义连接mysql的参数
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://warehouse102:3306/mysql"
    val userName = "root"
    val passWd = "root"

    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName(driver)
        DriverManager.getConnection(url, userName, passWd)
      },
      "select host, user, password from user where max_updates >= ? and max_updates <= ?",
      0,
      20,
      2,
      result => (result.getString(1), result.getString(2))
    )
    rdd.collect.foreach(println)

    sc.stop()
  }

}
