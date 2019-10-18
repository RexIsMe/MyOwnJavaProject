package com.atguigu.spark.core.partitioner

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
  * @title: DIYPartitioner
  * @projectName spark
  * @description:
  * 自定义分区器
  * 要实现自定义的分区器，你需要继承 org.apache.spark.Partitioner, 并且需要实现下面的方法:
  * 1.numPartitions
  * 该方法需要返回分区数, 必须要大于0.
  * 2.getPartition(key)
  * 返回指定键的分区编号(0到numPartitions-1)。
  * 3.equals
  * Java 判断相等性的标准方法。这个方法的实现非常重要，Spark 需要用这个方法来检查你的分区器对象是否和其他分区器实例相同，这样 Spark 才可以判断两个 RDD 的分区方式是否相同
  * 4.hashCode
  * 如果你覆写了equals, 则也应该覆写这个方法.
  * @author Rex
  * @date 2019/10/1117:51
  */
object DIYPartitioner {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Practice").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd1 = sc.parallelize(
      Array((10, "a"), (20, "b"), (30, "c"), (40, "d"), (50, "e"), (60, "f")),
      3)
    val rdd2: RDD[(Int, String)] = rdd1.partitionBy(new MyPartitioner(4))
    val rdd3: RDD[(Int, String)] = rdd2.mapPartitionsWithIndex((index, items) => items.map(x => (index, x._1 + " : " + x._2)))
    println(rdd3.collect.mkString(" "))

  }
}

class MyPartitioner(numPars: Int) extends Partitioner {
  override def numPartitions: Int = numPars


  override def getPartition(key: Any): Int = {
    1
  }
}
