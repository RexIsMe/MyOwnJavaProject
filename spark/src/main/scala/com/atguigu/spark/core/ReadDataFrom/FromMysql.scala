package com.atguigu.spark.core.ReadDataFrom

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: FromMysql
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/810:46
  */
class FromMysql {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
    val sc = new SparkContext(conf)
    //定义连接mysql的参数
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://hadoop201:3306/rdd"
    val userName = "root"
    val passWd = "aaa"

    val rdd: RDD[(Int, String)] = sc.parallelize(Array((110, "police"), (119, "fire")))
    // 对每个分区执行 参数函数
    rdd.foreachPartition(it => {
      Class.forName(driver)
      val conn: Connection = DriverManager.getConnection(url, userName, passWd)
      it.foreach(x => {
        val statement: PreparedStatement = conn.prepareStatement("insert into user values(?, ?)")
        statement.setInt(1, x._1)
        statement.setString(2, x._2)
        statement.executeUpdate()
      })
    })

  }

}
