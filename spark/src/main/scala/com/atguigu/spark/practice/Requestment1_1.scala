package com.atguigu.spark.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * @title: Requestment1
  * @projectName spark
  * @description: 问题：
  *              1、聚合在DRIVER中做的，没有利用分布式系统的优势，数据量大时会导致内存溢出
  * @author Rex
  * @date 2019/10/98:22
  */
object Requestment1_1 {


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

//    uvaRDD.foreach(println)

    // 5、从uvaRDD中取到自己想要的数据
    val acc: ListBuffer[CategoryCountInfo] = new ListBuffer[CategoryCountInfo]

    // TODO 这里将RDD 转换成为数组，这样数据才能保存到acc中，但也同时导致聚合过程在Driver中执行
    val actions: Array[UserVisitAction] = uvaRDD.collect()
    actions.foreach(
      ele => {
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
              acc.append(CategoryCountInfo(ele.click_category_id.toString, 1, 0, 0))
            } else if (ele.order_category_ids != "null") {
              ele.order_category_ids.split(",").foreach {
                oid => acc.append(CategoryCountInfo(oid, 0, 1, 0))
              }
            } else if (ele.pay_category_ids != "null") {
              ele.pay_category_ids.split(",").foreach {
                pid => acc.append(CategoryCountInfo(pid, 0, 0, 1))
              }
            }
          }
          case _ => Nil
        }
      }
    )

    // 6、将acc中的数据根据id做聚合
    val tuples: ListBuffer[(String, CategoryCountInfo)] = acc.map(
      ele => {
        (ele.categoryId, ele)
      }
    )

    val stringToTuples: Map[String, ListBuffer[(String, CategoryCountInfo)]] = tuples.groupBy(k => k._1)
    val stringToInfo: Map[String, CategoryCountInfo] = stringToTuples.map(ele => {
      val tuple: (String, CategoryCountInfo) = ele._2.reduce(
        (x, y) => {
          x._2.clickCount = x._2.clickCount + y._2.clickCount
          x._2.orderCount = x._2.orderCount + y._2.orderCount
          x._2.payCount = x._2.payCount + y._2.payCount
          x
        }
      )
      tuple
    })

    // 7、根据点击、下单、支付排序取前10
    /*val infoes: List[CategoryCountInfo] = stringToInfo.map(x => x._2).toList.sortBy(
//      x => (x.clickCount,x.orderCount,x.payCount)).take(10)
//      x => (x.clickCount,x.orderCount,x.payCount))(Ordering.Tuple3(Ordering.Long, Ordering.Long, Ordering.Long)).take(10)
    x => (x.clickCount,x.orderCount,x.payCount))(Ordering.Tuple3(Ordering.Long.reverse, Ordering.Long.reverse, Ordering.Long.reverse)).take(10)
    infoes.foreach(println)*/


    val result: List[(String, CategoryCountInfo)] = stringToInfo.toList.sortBy {
      case (x: String, y: CategoryCountInfo) => {
        (y.clickCount, y.orderCount, y.payCount)
      }
    }(Ordering.Tuple3(Ordering.Long.reverse, Ordering.Long.reverse, Ordering.Long.reverse)).take(10)
    result.foreach(println)
    // 9、关闭上下文
    sc.stop()

  }

}


/**
  * 用户访问动作表
  *
  * @param date               用户点击行为的日期
  * @param user_id            用户的ID
  * @param session_id         Session的ID
  * @param page_id            某个页面的ID
  * @param action_time        动作的时间点
  * @param search_keyword     用户搜索的关键词
  * @param click_category_id  某一个商品品类的ID
  * @param click_product_id   某一个商品的ID
  * @param order_category_ids 一次订单中所有品类的ID集合
  * @param order_product_ids  一次订单中所有商品的ID集合
  * @param pay_category_ids   一次支付中所有品类的ID集合
  * @param pay_product_ids    一次支付中所有商品的ID集合
  * @param city_id            城市 id
  */
case class UserVisitAction(date: String,
                           user_id: Long,
                           session_id: String,
                           page_id: Long,
                           action_time: String,
                           search_keyword: String,
                           click_category_id: Long,
                           click_product_id: Long,
                           order_category_ids: String,
                           order_product_ids: String,
                           pay_category_ids: String,
                           pay_product_ids: String,
                           city_id: Long)
case class CategoryCountInfo(var categoryId: String,
                             var clickCount: Long,
                             var orderCount: Long,
                             var payCount: Long)