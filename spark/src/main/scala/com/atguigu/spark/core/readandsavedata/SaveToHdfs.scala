package com.atguigu.spark.core.readandsavedata

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @title: SaveToHdfs
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/11/1910:41
  */
object SaveToHdfs {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("dwd_member_import")
      //硬编码级别高于属性设置，所以打包提交上去执行时需要注释
      .setMaster("local[*]")
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext

    //高可用hdfs访问配置（配置文件中有，可省略）

    val unit: RDD[String] = ssc.textFile("file:\\D:\\atguigu\\大数据\\项目实战\\2.资料\\01日志数据\\coursepay.log")
    ssc.hadoopConfiguration.set("fs.defaultFS", "hdfs://nameservice1")
    ssc.hadoopConfiguration.set("dfs.nameservices", "nameservice1")
    unit.saveAsTextFile("/user/atguigu/ods/coursepay.log")

//    val unit: RDD[String] = ssc.textFile("/user/atguigu/ods/salecourse.log")
//    unit.foreach(println)

    ssc.stop()
  }

}
