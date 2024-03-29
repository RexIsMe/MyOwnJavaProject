package com.atguigu.teacher.qz.controller

import com.atguigu.teacher.qz.service.DwsQzService
import com.atguigu.teacher.util.HiveUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DwsController {
  def main(args: Array[String]): Unit = {
//    System.setProperty("HADOOP_USER_NAME", "atguigu")
    val sparkConf = new SparkConf().setAppName("dws_qz_controller").setMaster("local[*]")
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext
    ssc.hadoopConfiguration.set("fs.defaultFS", "hdfs://nameservice1")
    ssc.hadoopConfiguration.set("dfs.nameservices", "nameservice1")
    HiveUtil.openDynamicPartition(sparkSession) //开启动态分区
    HiveUtil.openCompression(sparkSession) //开启压缩
    HiveUtil.useSnappyCompression(sparkSession) //使用snappy压缩
    val dt = "20190722"
    DwsQzService.saveDwsQzChapter(sparkSession, dt)
    DwsQzService.saveDwsQzCourse(sparkSession, dt)
    DwsQzService.saveDwsQzMajor(sparkSession, dt)
    DwsQzService.saveDwsQzPaper(sparkSession, dt)
    DwsQzService.saveDwsQzQuestionTpe(sparkSession, dt)
    DwsQzService.saveDwsUserPaperDetail(sparkSession, dt)
  }
}
