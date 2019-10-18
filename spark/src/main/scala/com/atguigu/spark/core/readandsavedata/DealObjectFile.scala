package com.atguigu.spark.core.readandsavedata

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: DealSequenceFile
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1118:06
  */
object DealObjectFile {

  def main(args: Array[String]): Unit = {

    //设置访问hadoop 的用户名
    System.setProperty("HADOOP_USER_NAME", "atguigu")
    val conf: SparkConf = new SparkConf().setAppName("text").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    // 生成sequenceFile
//    val value: RDD[(String, Int)] = sc.parallelize(Array(("a", 1),("b", 2),("c", 3)))
//    value.saveAsObjectFile("hdfs://warehouse102:9000/obj_file")

    val value: RDD[(String, Int)] = sc.objectFile[(String, Int)]("hdfs://warehouse102:9000/obj_file")
    value.collect().foreach(println)


    sc.stop()
  }

}
