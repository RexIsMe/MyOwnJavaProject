package com.atguigu.spark.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Requestment3_1
  * @projectName spark
  * @description: 单个页面跳转的转换率
  * @author Rex
  * @date 2019/10/915:10
  */
object Requestment3_1 {

  def main(args: Array[String]): Unit = {

    // 1、获取Spark上下文对象
    val conf: SparkConf = new SparkConf().setAppName("PageChangeRate").setMaster("local[*]")
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

    val ptp: List[(Int, Int)] = pids.zip(pids.tail)
    val ptpString: List[String] = ptp.map(t => t._1 + "-" + t._2)


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

    val pidToPid1: RDD[(String, Int)] = timeToPageIdRDD.flatMap(x => {
      val tuples: List[(String, Long)] = x.sortWith((x, y) => x._1 < y._1)
      //      val tuples: List[(String, Long)] = x.sortBy(t => t._1)(Ordering.String)
      val longs: List[Long] = tuples.map(x => x._2)

      val pidToPid: List[(Long, Long)] = longs.zip(longs.tail)
      pidToPid.map(t => ((t._1 + "-" + t._2), 1))
    })

    // 7、将6的数据做聚合得到((pid1-pid2), count)
    var pidToPidCount: collection.Map[String, Long] = pidToPid1.countByKey()

    // 8、根据pageId分组统计个数
    // TODO 按需求先过滤
    uvaRDD = uvaRDD.filter(uva => pids.contains(uva.page_id))

    pidToPidCount = pidToPidCount.filter( t => ptpString.contains(t._1))
    val groupByPid: RDD[(Long, Iterable[UserVisitAction])] = uvaRDD.groupBy(u => u.page_id)
    val pidToCount: RDD[(Long, Int)] = groupByPid.map(
      t => (t._1, t._2.size)
    )
    val map: Map[Long, Int] = pidToCount.collect().toMap


    // 9、便利7的结果，与8中对应数据做运算，得到转换率
    pidToPidCount.foreach(
      t => {
        val strings: Array[String] = t._1.split("-")
        val pid: String = strings(0)

        println( t._1 + "的跳转率为：" + (t._2.toDouble/map.getOrElse(pid.toLong, 1)))
      }
    )

    // 关闭上下文对象
    sc.stop()

  }


}
