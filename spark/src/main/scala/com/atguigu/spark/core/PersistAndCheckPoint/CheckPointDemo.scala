package com.atguigu.spark.core.PersistAndCheckPoint

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: CheckPointDemo
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1117:21
  */
object CheckPointDemo {
  def main(args: Array[String]): Unit = {
    // 要在SparkContext初始化之前设置, 都在无效
    System.setProperty("HADOOP_USER_NAME", "atguigu")
    val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
    val sc = new SparkContext(conf)
    // 设置 checkpoint的目录. 如果spark运行在集群上, 则必须是 hdfs 目录
    sc.setCheckpointDir("hdfs://warehouse102:9000/checkpoint")
    val rdd1 = sc.parallelize(Array("abc"))
    val rdd2: RDD[String] = rdd1.map(x => {
      println("do map......")
      x + " : " + System.currentTimeMillis()
    })

    /*
    标记 RDD2的 checkpoint.
    RDD2会被保存到文件中(文件位于前面设置的目录中), 并且会切断到父RDD的引用, 也就是切断了它向上的血缘关系
    该函数必须在job被执行之前调用.
    强烈建议把这个RDD序列化到内存中, 否则, 把他保存到文件的时候需要重新计算.
     */
    rdd2.cache()
    rdd2.checkpoint()
    println(rdd2.toDebugString)
    rdd2.collect().foreach(println)
    rdd2.collect().foreach(println)
    rdd2.collect().foreach(println)
  }
}
