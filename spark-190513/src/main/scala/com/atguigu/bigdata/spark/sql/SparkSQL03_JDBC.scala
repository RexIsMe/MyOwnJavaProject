package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, Row, SparkSession, TypedColumn}


object SparkSQL03_JDBC {

  def main(args: Array[String]): Unit = {

    // TODO 创建环境对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL03_JDBC")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._
    // TODO 读取数据
    val df: DataFrame = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://linux1:3306/rdd")
      .option("user", "root")
      .option("password", "000000")
      .option("dbtable", "user1")
      .load()

    df.show()

    // TODO 写入数据
    df.write.format("jdbc")
      .option("url", "jdbc:mysql://linux1:3306/rdd")
      .option("user", "root")
      .option("password", "000000")
      .option("dbtable", "user2")
      .save()

    //spark.read.jdbc()


    // TODO 释放资源
    spark.stop()

  }
}
