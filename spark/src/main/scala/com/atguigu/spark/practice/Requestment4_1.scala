package com.atguigu.spark.practice

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: Requestment4_1
  * @projectName spark
  * @description: 需求：
  *               得到指定页面的平均停留时间
  *
  *               分析：
  *               如计算页面id为1的停留时间，则应该得到所有1-x的时间总和再除以1-x的个数
  * @author Rex
  * @date 2019/10/108:33
  */
object Requestment4_1 {

  def main(args: Array[String]): Unit = {

    // 1、获取Spark上下文对象
    val conf: SparkConf = new SparkConf().setAppName("stayPageTime").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    // 2、从文件中读取原始数据
    val textRDD: RDD[String] = sc.textFile("input/user_visit_action.txt")

    // 3、将文件中的记录封装成对象，便于后续操作
    var uvaRDD: RDD[UserVisitAction] = textRDD.map(x => {
      val strings: Array[String] = x.split("_")
      new UserVisitAction(
        strings(0),
        strings(1).toLong,
        strings(2),
        strings(3).toLong,
        strings(4),
        strings(5),
        strings(6).toLong,
        strings(7).toLong,
        strings(8),
        strings(9),
        strings(10),
        strings(11),
        strings(12).toLong
      )
    })


    // TODO 新增需求：对指定的pid执行一下操作
    val pids: List[Int] = List(1,2,3,4,5,6)

    // 4、取到我们需要的字段，并转换格式（sessionId, pageId）
    // 5、根据sessionId分组，并根据时间戳升序排序
    // 6、将排好序的pageId转换成 ((pid1-pid2), 1)
    val sessionRDD: RDD[(String, Iterable[UserVisitAction])] = uvaRDD.groupBy(usa => usa.session_id)
    val timeToPageIdRDD: RDD[List[(String, Long)]] = sessionRDD.map(x => {
      val tuples: Iterable[(String, Long)] = x._2.map(
        ele => {
          (ele.action_time, ele.page_id)
        }
      )
      tuples.toList
    })

    /**
      * 得到（pid1-pid2, 时间差）这样的格式数据
      */
    val pidToPidTime: RDD[(String, Long)] = timeToPageIdRDD.flatMap(x => {
      val tuples: List[(String, Long)] = x.sortWith((x, y) => x._1 < y._1)
      //      val tuples: List[(String, Long)] = x.sortBy(t => t._1)(Ordering.String)

      val tuples2: List[((String, Long), (String, Long))] = tuples.zip(tuples.tail)
      tuples2.map {
        case (x, y) => {
          //计算时间差
          val format: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          val start: Date = format.parse(x._1)
          val end: Date = format.parse(y._1)
          val startCalendar: Calendar = Calendar.getInstance()
          startCalendar.setTime(start)
          val endCalendar: Calendar = Calendar.getInstance()
          endCalendar.setTime(end)
          val endMillis: Long = endCalendar.getTimeInMillis
          val startMillis: Long = startCalendar.getTimeInMillis

          ((x._2 + "-" + y._2), endMillis - startMillis)
        }
      }
    })

    /**
      * 修改格式为（pid1, 时间差）
      */
    val pidToTime: RDD[(String, Long)] = pidToPidTime.map(x => {
      val strings: Array[String] = x._1.split("-")
      (strings(0), x._2)
    })

    /**
      * 根据key聚合后，用总时间除以次数，得到平均停留时间
      */
    val pidToTimeList: RDD[(String, Iterable[Long])] = pidToTime.groupByKey()
    val result: RDD[(String, Double)] = pidToTimeList.map(x => (x._1, x._2.sum.toDouble/x._2.size))
    println("所有页面的停留时间")
    result.collect().foreach(println)

    /**
      * 过滤出指定的页面id
      */
    val resultAfterFilter: RDD[(String, Double)] = result.filter(x => pids.contains(x._1.toInt))
    println("最终结果:")
    resultAfterFilter.collect().toList.sortBy(x => x._2.toInt)(Ordering.Int.reverse).foreach(x => {
      println(x._1 + "," + x._2 + "ms")
    })

    // 关闭上下文对象
    sc.stop()

  }

}
