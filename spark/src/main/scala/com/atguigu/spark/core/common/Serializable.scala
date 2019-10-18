package com.atguigu.spark.core.common

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Serializable
  * @projectName spark
  * @description: 对象序列化问题的三种方式
  * @author Rex
  * @date 2019/9/3010:22
  */
object Serializable {

  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)

    val rdd: RDD[String] = sc.makeRDD(List("hello","hi","hey"))

    val searcher = new Searcher("hello")
//    val result: RDD[String] = searcher.getMatchedRDD1(rdd)
    val result: RDD[String] = searcher.getMatchedRDD2(rdd)
    result.collect.foreach(println)


    // 4. 关闭连接
    sc.stop()
  }


}


// query 为需要查找的子字符串
case class Searcher(val query: String){
  // 判断 s 中是否包括子字符串 query
  def isMatch(s : String) ={
    s.contains(query)
  }
  // 过滤出包含 query字符串的字符串组成的新的 RDD
  def getMatchedRDD1(rdd: RDD[String]) ={
    rdd.filter(this.isMatch)  //
  }
  // 过滤出包含 query字符串的字符串组成的新的 RDD
  def getMatchedRDD2(rdd: RDD[String]) ={
//    var query = this.query;
    rdd.filter(_.contains(query))
  }
}
