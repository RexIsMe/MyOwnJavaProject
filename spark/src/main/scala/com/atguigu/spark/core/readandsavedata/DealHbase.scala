//package com.atguigu.spark.core.readandsavedata
//
//import java.sql.DriverManager
//
//import org.apache.spark.rdd.JdbcRDD
//import org.apache.spark.{SparkConf, SparkContext}
//
///**
//  * @title: DealText
//  * @projectName spark
//  * @description: TODO
//  * @author Rex
//  * @date 2019/10/1117:56
//  */
//object DealHbase {
//
//  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
//    val sc = new SparkContext(conf)
//
//
//    sc.stop()
//  }
//
//  def readFromHbase(sc : SparkContext):Unit = {
//    val hbaseConf: Configuration = HBaseConfiguration.create()
//    hbaseConf.set("hbase.zookeeper.quorum", "hadoop201,hadoop202,hadoop203")
//    hbaseConf.set(TableInputFormat.INPUT_TABLE, "student")
//
//    val rdd: RDD[(ImmutableBytesWritable, Result)] = sc.newAPIHadoopRDD(
//      hbaseConf,
//      classOf[TableInputFormat],
//      classOf[ImmutableBytesWritable],
//      classOf[Result])
//
//    val rdd2: RDD[String] = rdd.map {
//      case (_, result) => Bytes.toString(result.getRow)
//    }
//    rdd2.collect.foreach(println)
//  }
//
//}
