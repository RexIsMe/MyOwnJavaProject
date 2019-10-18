package com.atguigu.spark.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * @title: Requestment1
  * @projectName spark
  * @description: 为了解决1_2中的问题
  *              使用map累加器替换list累加器，并在merge方法中做聚合
  * @author Rex
  * @date 2019/10/98:22
  */
object Requestment1_3 {

  def main(args: Array[String]): Unit = {

    // 1. 创建 SparkConf对象, 并设置 App名字
    val conf: SparkConf = new SparkConf().setAppName("Top10").setMaster("local[*]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)
    // 3、读取文件的数据
    val textRDD: RDD[String] = sc.textFile("input/user_visit_action.txt")

//    textRDD.foreach(println)

    // 4、将数据解析封装成对象UserVisitAction
    val uvaRDD: RDD[UserVisitAction] = textRDD.map(
      line => {
        val strings: Array[String] = line.split("_")
        val action: UserVisitAction = new UserVisitAction(
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
        action
      }
    )

    // 5、从uvaRDD中取到自己想要的数据
    var mapAccmu = new MyMapAccumulator
    sc.register(mapAccmu, "mapAccmu")

    // TODO 这里将RDD 转换成为数组，这样数据才能保存到acc中，但也同时导致聚合过程在Driver中执行
    val actions: Array[UserVisitAction] = uvaRDD.collect()
    actions.foreach(
      ele => mapAccmu.add(ele)
    )

    // 7、根据点击、下单、支付排序取前10
    val tuples: List[(String, CategoryCountInfo)] = mapAccmu.value.toList.sortBy {
      case (x: String, y: CategoryCountInfo) => {
        (y.clickCount, y.orderCount, y.payCount)
      }
    }(Ordering.Tuple3(Ordering.Long.reverse, Ordering.Long.reverse, Ordering.Long.reverse)).take(10)

    val result: List[CategoryCountInfo] = tuples.map(x => x._2)
    result.foreach(println)
    // 9、关闭上下文
    sc.stop()

  }

}


class MyMapAccumulator extends AccumulatorV2[UserVisitAction, mutable.Map[String, CategoryCountInfo]]{

  private var map: mutable.HashMap[String, CategoryCountInfo] = new mutable.HashMap[String, CategoryCountInfo]()

  override def isZero: Boolean = {
    map.isEmpty
  }

  //TODO 这里，按方法名理解应该是复制，但这样new一个新的，里面是没有值的
  override def copy(): AccumulatorV2[UserVisitAction, mutable.Map[String, CategoryCountInfo]] = {
    new MyMapAccumulator
//    val accumulator: MyMapAccumulator = new MyMapAccumulator
//    accumulator.map = map.clone()
//    accumulator
  }

  override def reset(): Unit = {
    map.clear()
  }

  override def add(ele: UserVisitAction): Unit = {
    val info: CategoryCountInfo = new CategoryCountInfo("0", 0, 0, 0)
    ele match {
      case UserVisitAction(_: String,
      _: Long,
      _: String,
      _: Long,
      _: String,
      _: String,
      _: Long,
      _: Long,
      _: String,
      _: String,
      _: String,
      _: String,
      _: Long) => {
        if (ele.click_category_id != -1) {
          info.categoryId = ele.click_category_id.toString
          val info2: CategoryCountInfo = map.getOrElse(ele.click_category_id.toString, info)

          info2.clickCount = info2.clickCount + 1L

          map.put(ele.click_category_id.toString, info2)
        } else if (ele.order_category_ids != "null") {
          ele.order_category_ids.split(",").foreach {
            oid => {
              val info: CategoryCountInfo = map.getOrElse(oid, new CategoryCountInfo(oid, 0, 0, 0))
              info.orderCount = info.orderCount + 1L
              map.put(oid, info)
            }
          }
        } else if (ele.pay_category_ids != "null") {
          ele.pay_category_ids.split(",").foreach {
            pid => {
              val info: CategoryCountInfo = map.getOrElse(pid, new CategoryCountInfo(pid, 0, 0, 0))
              info.payCount = info.payCount + 1L
              map.put(pid, info)
            }
          }
        }
      }
      case _ => Unit
    }
  }

  override def merge(other: AccumulatorV2[UserVisitAction, mutable.Map[String, CategoryCountInfo]]): Unit = {
    val stringToInfo: mutable.HashMap[String, CategoryCountInfo] = map.foldLeft(map)((x, y) => {
      val info: CategoryCountInfo = new CategoryCountInfo(y._1, 0, 0, 0)
      val info2: CategoryCountInfo = x.getOrElse(y._1, info)

      info2.clickCount = info2.clickCount + y._2.clickCount
      info2.orderCount = info2.orderCount + y._2.orderCount
      info2.payCount = info2.payCount + y._2.payCount

      x(y._1) = info2

      x
    })
    stringToInfo
  }

  override def value: mutable.Map[String, CategoryCountInfo] = {
    map
  }
}