package com.atguigu.teacher.sellcourse.controller

import com.atguigu.teacher.sellcourse.service.DwsSellCourseService
import com.atguigu.teacher.util.HiveUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * 对join优化
  * 1、小表join大表时，广播小表
  * 2、大表join大表时，利用分桶做到SMB join(即sortMerge Bucket join)
  * com.atguigu.teacher.sellcourse.service.DwdSellCourseService#importCoursePay2(org.apache.spark.SparkContext, org.apache.spark.sql.SparkSession)
  * 需要对数据先做分桶处理，分桶列==join列==sort列
  */
object DwsSellCourseController4 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("dws_sellcourse_import")
      .set("spark.sql.autoBroadcastJoinThreshold", "1")
      .set("spark.sql.shuffle.partitions", "15")
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext
    ssc.hadoopConfiguration.set("fs.defaultFS", "hdfs://nameservice1")
    ssc.hadoopConfiguration.set("dfs.nameservices", "nameservice1")
    HiveUtil.openDynamicPartition(sparkSession)
    HiveUtil.openCompression(sparkSession)
    DwsSellCourseService.importSellCourseDetail4(sparkSession, "20190722")
  }
}
