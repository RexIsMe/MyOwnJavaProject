package com.atguigu.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * @title: WordCountAll
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/88:29
  */
object WordCountAll {

  def main(args: Array[String]): Unit = {

    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)

    val value: RDD[(String, Int)] = sc.makeRDD(Array(("a", 1), ("b", 2), ("a", 3), ("b", 4), ("a", 5), ("b", 6)))

    value.cache()

    method1(value)
    method2(value)
    method3(value)
    method4(value)
    method5(value)
    method6(value)
    method7(value)
    method8(value)
    method9(value)

    sc.stop()
  }


  def method1(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method1:Begin ===============")
    val value: RDD[(Int, String)] = rdd.flatMap(t => {
      var result = new ListBuffer[(Int, String)]
      for (i <- 1 to t._2) {
        result.append((1, t._1))
      }
      result
    })

    val tupleToLong: collection.Map[(Int, String), Long] = value.countByValue()
    println(tupleToLong.map(t => (t._1._2, t._2)).mkString)
    println("============== Method1:End ===============")
    println("\r\n\r\n")
  }

  def method2(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method2:Begin ===============")
    val value: RDD[(String, Int)] = rdd.flatMap(t => {
      var result = new ListBuffer[(String, Int)]
      for (i <- 1 to t._2) {
        result.append((t._1, 1))
      }
      result
    })
    println(value.countByKey())
    println("============== Method2:End ===============")
    println("\r\n\r\n")
  }

  def method3(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method3:Begin ===============")
    val rdd1 = rdd.groupBy { case t => t._1 }
    val rdd2 = rdd1.map {
      case (words, t) => {
        (words, t.map {
          case (word, num) => num
        })
      }
    }
    val result: RDD[(String, Int)] = rdd2.map {
      case (word, t) => (word, t.sum)
    }

    println(result.collect.mkString)
    println("============== Method3:End ===============")
    println("\r\n\r\n")
  }

  def method4(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method4:Begin ===============")
    val value: RDD[(String, (Int, Int))] = rdd.combineByKey((_, 1), (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1), (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
//    val collect: Array[(String, Double)] = value.map(t => (t._1, t._2._1.toDouble / t._2._2)).collect
    val collect: Array[(String, Double)] = value.map(t => (t._1, t._2._1.toDouble)).collect

    println(collect.mkString)
    println("============== Method4:End ===============")
    println("\r\n\r\n")
  }

  def method5(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method5:Begin ===============")
    val collect: Array[(String, Int)] = rdd.foldByKey(0)(_ + _).collect

    println(collect.mkString)
    println("============== Method5:End ===============")
    println("\r\n\r\n")
  }

  def method6(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method6:Begin ===============")
    val value: RDD[(String, Int)] = rdd.aggregateByKey(0)((x: Int, y: Int) => {
//      Math.max(x, y)
      x + y
    }, (x: Int, y: Int) => {
      x + y
    })
    value.collect().foreach(println)
    println("============== Method6:End ===============")
    println("\r\n\r\n")
  }

  def method7(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method7:Begin ===============")
    // 转换算子 —— groupByKey
    val rdd2: RDD[(String, Iterable[Int])] = rdd.groupByKey()

    val rdd3: RDD[(String, Int)] = rdd2.map {
      case (c, datas) => {
        (c, datas.sum)
      }
    }
    rdd3.collect().foreach(println)
    println("============== Method7:End ===============")
    println("\r\n\r\n")
  }

  def method8(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method8:Begin ===============")
    // 转换算子 —— reduceByKey
    //val value: RDD[(String, Int)] = rdd.reduceByKey((x,y)=>{x+y})
    val value: RDD[(String, Int)] = rdd.reduceByKey(_ + _)

    value.collect().foreach(println)
    println("============== Method8:End ===============")
    println("\r\n\r\n")
  }

  def method9(rdd: RDD[(String, Int)]): Unit = {
    println("============== Method9:Begin ===============")
    val value: RDD[(String, Int)] = rdd.flatMap(t => {
      var result = new ListBuffer[(String, Int)]
      for (i <- 1 to t._2) {
        result.append((t._1, 1))
      }
      result
    })

    val value1: RDD[(String, Iterable[Int])] = value.groupByKey()

    val value2: RDD[(String, Int)] = value1.map(t => (t._1, t._2.sum))
    println(value2.collect.mkString)
    println("============== Method9:End ===============")
    println("\r\n\r\n")
  }


}
