package com.atguigu.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: _1
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/87:55
  */
object _3 {

  def main(args: Array[String]): Unit = {
//    // 1. 创建 SparkConf对象, 并设置 App名字
//    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
//    // 2. 创建SparkContext对象
//    val sc = new SparkContext(conf)
//
//
//
//    val rdd = sc.parallelize(List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8)))
//    val value: RDD[(String, Int)] = rdd.aggregateByKey(Int.MinValue)(math.max(_, _), _ +_)
//    val tuples: Array[(String, Int)] = value.collect()
//    println(tuples.mkString)
//
//    sc.stop()




    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a",1),("b",2), ("b",3),
      ("a",3), ("b",4), ("a", 5)), 2)
    val value: RDD[(String, Int)] = rdd.aggregateByKey(0)((x:Int,y:Int)=>{Math.max(x,y)}, (x:Int,y:Int)=>{x+y})
    value.collect().foreach(println)

    sc.stop()
  }

}
