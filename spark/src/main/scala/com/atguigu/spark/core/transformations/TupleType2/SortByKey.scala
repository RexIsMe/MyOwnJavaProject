package com.atguigu.spark.core.transformations.TupleType2

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: SortByKey
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1113:58
  */
object SortByKey {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value1: RDD[(Int, String)] = sc.makeRDD(List((1,"a"), (2,"b"),(3,"c"),(4,"d"),(5,"e")))
    value1.sortByKey(false).collect().foreach(println)

    val value2: RDD[(String, String)] = sc.makeRDD(List(("a","a"), ("b","b"),("c","c"),("d","d"),("e","e")))
    value2.sortByKey().collect().foreach(println)

    val value3: RDD[((String, Int), String)] = sc.makeRDD(List((("a",11),"a"), (("b", 12),"b"),(("c", 13),"c"),(("d", 14),"d"),(("e", 15),"e")))
    value3.sortByKey(false).collect().foreach(println)
    val result: RDD[((String, Int), String)] = value3.sortBy {
      case ((_, v), _) => {
        v
      }
    }
    result.collect().foreach(println)

    sc.stop()
  }

}
