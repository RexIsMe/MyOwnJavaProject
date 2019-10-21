package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark22_RDD_DEMO {

  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 从日志文件中获取原始数据 : (ts pri ci use ad)
    val lineRDD: RDD[String] = sc.textFile("input/agent.log")

    // TODO 4. 将数据进行分解，保留业务需要的数据: (pri, adv)
    // TODO 5. 将保留的业务数据的结构进行改变：(pri, adv) => (pri-adv,1)
    val priAndAdvToOneRDD: RDD[(String, Int)] = lineRDD.map(
      line => {
        val datas: Array[String] = line.split(" ")
        (datas(1) + "_" + datas(4), 1)
      }
    )
    // TODO 6. 将转换结构后的数据进行统计聚合:(pri-adv,1) => (pri-adv,sum)
    val priAndAdvToSumRDD: RDD[(String, Int)] = priAndAdvToOneRDD.reduceByKey(_+_)

    // TODO 7. 将统计结果进行结构转换：(pri-adv,sum) => (pri, (adv,sum))
    val priToAdvAndSumRDD: RDD[(String, (String, Int))] = priAndAdvToSumRDD.map {
      case (priAdv, sum) => {
        val keys: Array[String] = priAdv.split("_")
        (keys(0), (keys(1), sum))
      }
    }

    // TODO 8. 将相同的省份的数据放置在一起 ： (pri, Iterator(adv,sum))
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = priToAdvAndSumRDD.groupByKey()

    // TODO 9. 将同一个省份的广告点击数据进行排序（降序）
    // TODO 10. 将排序后的数据取前三名
    val resultRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(
      datas => {
        datas.toList.sortWith(
          (x, y) => {
            x._2 > y._2
          }
        ).take(3)
      }
    )



    resultRDD.collect().foreach(println)
    // TODO 11. 释放连接
    sc.stop()
  }
}
