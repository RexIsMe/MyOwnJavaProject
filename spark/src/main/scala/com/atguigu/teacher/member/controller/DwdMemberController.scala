package com.atguigu.teacher.member.controller

import com.atguigu.teacher.member.service.EtlDataService
import com.atguigu.teacher.util.HiveUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * 将hdfs上ods层数据经过出来了保存到hive的dwd数据库中
  */
object DwdMemberController {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("dwd_member_import")
      //硬编码级别高于属性设置，所以打包提交上去执行时需要注释
      .setMaster("local[*]")
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext

    //高可用hdfs访问配置（配置文件中有，可省略）
    ssc.hadoopConfiguration.set("fs.defaultFS", "hdfs://nameservice1")
    ssc.hadoopConfiguration.set("dfs.nameservices", "nameservice1")
    HiveUtil.openDynamicPartition(sparkSession) //开启动态分区
    HiveUtil.openCompression(sparkSession) //开启压缩
    //    HiveUtil.useSnappyCompression(sparkSession) //使用snappy压缩
    //对用户原始数据进行数据清洗 存入bdl层表中
    EtlDataService.etlBaseAdLog(ssc, sparkSession) //导入基础广告表数据
    EtlDataService.etlBaseWebSiteLog(ssc, sparkSession) //导入基础网站表数据
    EtlDataService.etlMemberLog(ssc, sparkSession) //清洗用户数据
    EtlDataService.etlMemberRegtypeLog(ssc, sparkSession) //清洗用户注册数据
    EtlDataService.etlMemPayMoneyLog(ssc, sparkSession) //导入用户支付情况记录
    EtlDataService.etlMemVipLevelLog(ssc, sparkSession) //导入vip基础数据
  }
}
