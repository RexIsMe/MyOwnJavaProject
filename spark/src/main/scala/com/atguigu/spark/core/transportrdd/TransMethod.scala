package com.atguigu.spark.core.transportrdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @title: TransMethod
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1116:40
  */
object TransMethod {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SerDemo").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val rdd: RDD[String] = sc.parallelize(Array("hello world", "hello atguigu", "atguigu", "hahah"), 2)
    val searcher = new SearcherKryo("hello")
    val result: RDD[String] = searcher.getMatchedRDD1(rdd)
    result.collect.foreach(println)
  }

}

// query 为需要查找的子字符串
//class Searcher(val query: String) extends Serializable {
class SearcherKryo(val query: String){
  // 判断 s 中是否包括子字符串 query
  def isMatch(s : String) ={
    s.contains(query)
  }
  // 过滤出包含 query字符串的字符串组成的新的 RDD
  def getMatchedRDD1(rdd: RDD[String]) ={
//    报未序列化的根本原因是这里使用了对象，所以避免对象的使用就可以不用做序列化
    //    rdd.filter(isMatch)

    var t = query
    rdd.filter(_.contains(t))
  }
  // 过滤出包含 query字符串的字符串组成的新的 RDD
  def getMatchedRDD2(rdd: RDD[String]) ={
    rdd.filter(_.contains(query))
  }
}