package com.atguigu.teacher.member.controller

import com.atguigu.teacher.member.service.DwsMemberService
import com.atguigu.teacher.util.HiveUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DwsMemberController {

  def main(args: Array[String]): Unit = {
//    System.setProperty("HADOOP_USER_NAME", "hdfs")
    val sparkConf = new SparkConf().setAppName("dws_member_import")
    .setMaster("local[*]")
    //spark自己的序列化方式（比java的更轻量）
    //      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    //      .registerKryoClasses(Array(classOf[DwsMember]))
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext
    ssc.hadoopConfiguration.set("fs.defaultFS", "hdfs://nameservice1")
    ssc.hadoopConfiguration.set("dfs.nameservices", "nameservice1")
    HiveUtil.openDynamicPartition(sparkSession) //开启动态分区
    HiveUtil.openCompression(sparkSession) //开启压缩
        HiveUtil.useSnappyCompression(sparkSession) //使用snappy压缩
    DwsMemberService.importMember(sparkSession, "20190722") //根据用户信息聚合用户表数据
    DwsMemberService.importMemberUseApi(sparkSession, "20190722")
  }
}
