package com.atguigu.spark.SQL

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, MapType, StringType, StructField, StructType}

/**
  * @title: SparkSQL04_Hive
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1119:38
  */
object SparkSQL04_Hive {

    def main(args: Array[String]): Unit = {
      val spark: SparkSession = SparkSession
        .builder()
        .master("local[*]")
        .appName("Test")
//        .enableHiveSupport()
        .getOrCreate()
      import spark.implicits._
      import spark.sql

      sql("select * from emp").show
    }
}
