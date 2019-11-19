package com.atguigu.train

import java.sql.Timestamp

import com.alibaba.fastjson.JSONObject
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.parsing.json.JSON
/**
  * @title: UserRegistETL
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/11/1510:05
  */
object UserRegistETL {

  val userName:String = ""
  val phoneNum:String = ""
  val password:String = ""

  val fileName:String = "baseadlog.log";
//  val fileName:String = "baswewebsit.log";
//  val fileName:String = "member.log";
//  val fileName:String = "memberRegtype.log";
//  val fileName:String = "pcenterMemViplevel.log";
//  val fileName:String = "pcentermempaymoney.log";

  val tableName:String = "dwd.dwd_base_ad";
//  val tableName:String = "dwd.dwd_base_website";
//  val tableName:String = "dwd.dwd_member";
//  val tableName:String = "dwd.dwd_member_regtype";
//  val tableName:String = "dwd.dwd_vip_level";
//  val tableName:String = "dwd.dwd_pcentermempaymoney";

  def main(args: Array[String]): Unit = {

    // 1. 创建 SparkConf对象, 并设置 App名字
    /*val conf: SparkConf = new SparkConf().setAppName("Top10").setMaster("local[*]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)*/

    val spark = SparkSession.builder().appName("OfflineSpark")
      .master("local[1]")
      .config("spark.sql.warehouse.dir","hdfs://aliyun102:9000/user/hive/warehouse/")
      .enableHiveSupport()
      .getOrCreate()
    val sc = spark.sparkContext

    import spark.implicits._
    sc.hadoopConfiguration.set("fs.defaultFS", "hdfs://nameservice1")
    sc.hadoopConfiguration.set("dfs.nameservices", "nameservice1")
    sc.hadoopConfiguration.set("dfs.ha.namenodes.cdhservice", "namenode36,namenode105")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.cdhservice.namenode36", "cdh1:8020")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.cdhservice.namenode105", "cdh3:8020")
    sc.hadoopConfiguration.set("dfs.client.failover.proxy.provider.cdhservice", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider")

    //3.读取hdfs中“ods”的数据
    val logRdd: RDD[String] = sc.textFile("hdfs://nameservice1/user/atguigu/ods/baseadlog.log")

    //4.解析、清洗数据
    //先过滤格式不符合的
    val value: RDD[String] = logRdd.filter(fliterNoJson(_))

    //对指定数据做脱敏
//    val value2: RDD[(Int, String, String)] = value.map(desensitization(_))

//    value2.foreach(println)

    //5.保存到hive的dwd数据库
    val frame: DataFrame = value.toDF("adid", "adname", "dn")
    frame.createOrReplaceTempView("tempView")

    //修改hive属性
    import spark.sql
    sql("set hive.exec.dynamic.partition=true")
    sql("set hive.exec.dynamic.partition.mode=nonstrict")

    val frame2: DataFrame = spark.sql("select * from tempView")

    frame2.write
      .insertInto("dwd.dwd_base_ad")
    // 9、关闭上下文
    sc.stop()
  }

  /**
    * 指定信息脱敏
    */
  def desensitization(str: String): (String, String, String) ={
    val json: JSONObject = com.alibaba.fastjson.JSON.parseObject(str)
    val value1: String = json.getString(userName)
    if(value1 != null){
      json.put(userName, 123)
    }

    val value2: String = json.getString(phoneNum)
    if(value2 != null){
      json.put(phoneNum, phoneNumRule(value2))
    }

    val value3: String = json.getString(password)
    if(value3 != null){
      json.put(password, "******")
    }

//    val result: String = json.toString
//    result


    (json.getString("adid"), json.getString("adname"), json.getString("dn"))
  }

  /**
    * 手机号脱敏规则
    * @param pn
    * @return
    */
  def phoneNumRule(pn:String): String ={
    val pre: String = pn.substring(0,1)
    val suf: String = pn.substring(pn.length - 1)

    pre + "******" + suf
  }

  /**
    * 过滤掉非json的数据
    * @param str
    * @return
    */
  def fliterNoJson(str: String): Boolean ={
    val js = JSON.parseFull(str)//类型为 string ->list[String] Map 映射
    val result:Boolean = js match {
      case Some(map: Map[String, String]) =>
        true
      // 匹配上所做的处理
      case None =>
        false
      //为空所做的处理
      case _ =>
        false
      //没有匹配上既是错误
    }
    result
  }

}
