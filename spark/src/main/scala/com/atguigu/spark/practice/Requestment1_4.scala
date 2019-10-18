package com.atguigu.spark.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{immutable, mutable}
import scala.collection.mutable.ListBuffer

/**
  * @title: Requestment1
  * @projectName spark
  * @description: 1_3的实现没什么大问题了，但是在map累加器中，由于In的结构设计的不够合理，导致add和merge方法实现有点麻烦，故再针对这个优化一下
  * @author Rex
  * @date 2019/10/98:22
  */
object Requestment1_4 {

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


    // 5、将UserVisitAction对象转换成((categaryId, "action"), 1)的格式

    // 5、从uvaRDD中取到自己想要的数据
    var goodMapAccmu = new GoodMapAccumulator
    sc.register(goodMapAccmu, "goodMapAccmu")

    // TODO 这里将RDD 转换成为数组，这样数据才能保存到acc中，但也同时导致聚合过程在Driver中执行
    val actions: Array[UserVisitAction] = uvaRDD.collect()
    actions.foreach(
      ele => goodMapAccmu.add(ele)
    )

//    goodMapAccmu.value.foreach(println)
    // 7、根据点击、下单、支付排序取前10
    // TODO 这样拆解成map会因为key重复，导致数据丢失
//    val stringToTuple: mutable.Map[String, (String, Long)] = goodMapAccmu.value.map(x => {
//      (x._1._1, (x._1._2, x._2))
//    })


    // TODO groupBy 不是只能根据key来做分组，它可以根据指定方式
    val stringToTupleToLong: Map[String, mutable.Map[(String, String), Long]] = goodMapAccmu.value.groupBy(x => x._1._1)
    val value22: immutable.Iterable[CategoryCountInfo] = stringToTupleToLong.map(x => {
      new CategoryCountInfo(x._1, x._2.getOrElse((x._1, "click"), 0L), x._2.getOrElse((x._1, "click"), 0L), x._2.getOrElse((x._1, "click"), 0L))
    })

    val infoes: List[CategoryCountInfo] = value22.toList.sortBy(x => (x.clickCount, x.orderCount, x.payCount))(Ordering.Tuple3(Ordering.Long.reverse,Ordering.Long.reverse,Ordering.Long.reverse)).take(10)
    infoes.foreach(println)




    // 9、关闭上下文
    sc.stop()

  }

}


class GoodMapAccumulator extends AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]]{

  private val map: mutable.HashMap[(String, String), Long] = new mutable.HashMap[(String, String), Long]()

  override def isZero: Boolean = {
    map.isEmpty
  }

  override def copy(): AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]] = {
    new GoodMapAccumulator
  }

  override def reset(): Unit = {
    map.clear()
  }

  override def add(visitAction: UserVisitAction): Unit = {
    visitAction match {
      case UserVisitAction(date: String,
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
      city_id: Long) => {
        if (visitAction.click_category_id != -1) {
          var i:Long = map.getOrElse((visitAction.click_category_id.toString, "click"), 0L)
          map.put((visitAction.click_category_id.toString, "click"), i + 1L)
        } else if (visitAction.order_category_ids != "null") {
          visitAction.order_category_ids.split(",").foreach {
            oid => {
              var i:Long = map.getOrElse((oid, "order"), 0L)
              map.put((oid, "order"), i + 1L)
            }
          }
        } else if (visitAction.pay_category_ids != "null") {
          visitAction.pay_category_ids.split(",").foreach {
            pid => {
              var i:Long = map.getOrElse((pid, "pay"), 0L)
              map.put((pid, "pay"), i + 1L)
            }
          }
        }
      }
      case _ => Unit
    }
  }

  override def merge(other: AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]]): Unit = {
    map.foldLeft(map)(
      (x, y) => {
        val count: Long = x.getOrElse(y._1, 0)
        x.update(y._1, count + y._2)
        x
      }
    )
  }

  override def value: mutable.Map[(String, String), Long] = {
    map
  }
}