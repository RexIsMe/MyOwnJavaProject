package com.atguigu.spark.SQL

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @title: UDAFTest
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1015:40
  */
object UDAFTest {

  def main(args: Array[String]): Unit = {
    // 创建一个新的 SparkSession 对象
    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("Word Count")
      .getOrCreate()
    // 导入用到隐式转换.  如果想要使用: $"age" 则必须导入
//    val df: DataFrame = spark.read.json("file://" + ClassLoader.getSystemResource("input/1.json").getPath)
    val df: DataFrame = spark.read.json("input/1.json")
    // 打印信息
    df.show
    // 查找年龄大于19岁的
    import spark.implicits._
    df.filter( $"age" > 19).show

    // 创建临时表
    df.createTempView("user")
    spark.sql("select * from user where age > 19").show

    //关闭连接
    spark.stop()

  }


}
