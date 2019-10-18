package com.atguigu.spark.core.common

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: WordCount
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/9/2713:59
  */
object Practice1 {

  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)

    //读取数据
    val lineString: RDD[String] = sc.textFile("data/input/")
    //    val lineString: RDD[String] = sc.makeRDD(List("1516609143867 6 7 64 16","1516609143869 9 4 75 18","1516609143869 1 7 87 12"))
    /**
      * 1516609143867 6 7 64 16
      * 1516609143869 9 4 75 18
      * 1516609143869 1 7 87 12
      */
    //切割字符串成各个编号
    val idListRDD: RDD[Array[String]] = lineString.map(line => line.split(" "))

    //提取需要的数据并将格式转换为（省id_广告id,1）
    val _14CountRDD: RDD[(String, Int)] = idListRDD.map(x => {
      (x(1) + "_" + x(4), 1)
    })
    //根据key聚合，得到（省id_广告id,count）
    val wordToCountRDD: RDD[(String, Int)] = _14CountRDD.reduceByKey(_ + _)

    //转换格式为（省id，广告id_count）
    val value: RDD[(String, (String, Int))] = wordToCountRDD.map(t => {
      val strings: Array[String] = t._1.split("_")
      (strings(0), (strings(1), t._2))
    })

    //根据省份合并
    val value1: RDD[(String, Iterable[(String, Int)])] = value.groupByKey()

    //将value根据Int排序，方式一
    //    val value2: RDD[(String, List[(String, Int)])] = value1.map(x => {
    //      (x._1, x._2.toList.sortWith((x, y) => {
    //        x._2 > y._2
    //      }))
    //    })

    //方式二
    val value2: RDD[(String, List[(String, Int)])] = value1.map {
      case (x: String, y: Iterable[(String, Int)]) => {
        val tuples: List[(String, Int)] = y.toList.sortWith((x, y) => {
          x._2 > y._2
        })

        (x, tuples)
      }
    }


    /*
    //方式三
    val value2: RDD[(String, List[(String, Int)])] = value1.mapValues(it => {
      it.toList.sortWith((x, y) => {
        x._2 > y._2
      })
    })*/
    val value3: RDD[(String, List[(String, Int)])] = value2.map(ele => {
      (ele._1, ele._2.take(3))
    })

    println(value3.collect().mkString(",\r\n"))


    /**
      * 期望结果
      * （省编号，List((广告编号，点击次数)，(广告编号，点击次数)，(广告编号，点击次数))）
      * （省编号，List((广告编号，点击次数)，(广告编号，点击次数)，(广告编号，点击次数))）
      * （省编号，List((广告编号，点击次数)，(广告编号，点击次数)，(广告编号，点击次数))）
      */

    //关闭连接
    sc.stop()
  }


}
