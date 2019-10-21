package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}


object SparkSQL01_Basic {

  def main(args: Array[String]): Unit = {

    // TODO 创建环境对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Basic")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    // 当SparkSql需要进行转换时，必须导入隐式转换。
    // 这里的spark不是包名，是sparkSession对象名称
    import spark.implicits._

    // TODO 创建DF, DS

    val df: DataFrame = spark.read.json("input/user.json")
    //df.show()
    /*
        val users: Seq[User] = Seq( User(1,"zhangsan",30), User(2, "lisi", 40) )
        val ds: Dataset[User] = users.toDS
        ds.show
         */

    // TODO RDD, DF, DS之间的转换
    /*
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1, "zhangsan", 30)))

    // RDD -> DF
    val df1: DataFrame = rdd.toDF("id", "name", "age")
    // DF -> RDD
    val rdd1: RDD[Row] = df1.rdd

    // RDD -> DS
    val userRDD: RDD[User] = rdd.map(t=>User(t._1, t._2, t._3))
    val ds1: Dataset[User] = userRDD.toDS()

    // DS -> RDD
    val rdd2: RDD[User] = ds1.rdd

    // DF -> DS
    val ds2: Dataset[User] = df1.as[User]
    // DS -> DF
    val df2: DataFrame = ds2.toDF()
     */

    // 使用SQL语法访问数据
    //df.createOrReplaceTempView("user")

    //spark.sql("select * from user").show

    // 使用DSL语法访问数据
    //df.select("age").show()

    //val rdd: RDD[Row] = df.rdd
    //rdd.foreach(row=>row.get)

    // TODO 释放资源
    spark.stop()

  }
}
case class User(id:Int, name:String, age:Int)
