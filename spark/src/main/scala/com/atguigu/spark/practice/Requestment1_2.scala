package com.atguigu.spark.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * @title: Requestment1
  * @projectName spark
  * @description: 用list累加器使得过滤和数据转换从Driver转移到Executor
  *              问题：
  *              1、虽然不会有内存溢出的问题了，但是这种方式分两次在Executor中执行的，效率不高
  * @author Rex
  * @date 2019/10/98:22
  */
object Requestment1_2 {

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
    var listAccumulator = new ListAccumulator
    sc.register(listAccumulator)

    // TODO 这里将RDD 转换成为数组，这样数据才能保存到acc中，但也同时导致聚合过程在Driver中执行
    val actions: Array[UserVisitAction] = uvaRDD.collect()
    actions.foreach(
      ele => listAccumulator.add(ele)
    )

//    println(acc.size)
//    acc.foreach(println)

    // 6、将acc中的数据根据id做聚合
    val value: RDD[CategoryCountInfo] = sc.makeRDD(listAccumulator.value)
    val value2: RDD[(String, CategoryCountInfo)] = value.map(
      x => (x.categoryId, x)
    )
    val value3: RDD[(String, CategoryCountInfo)] = value2.reduceByKey((x, y) => {
      x.payCount = x.payCount + y.payCount
      x.clickCount = x.clickCount + y.clickCount
      x.orderCount = x.orderCount + y.orderCount
      x
    })
    val stringToInfo: RDD[CategoryCountInfo] = value3.map(x => x._2)


    // 7、根据点击、下单、支付排序取前10
    val result: List[CategoryCountInfo] = stringToInfo.collect().toList.sortBy {
      case (y: CategoryCountInfo) => {
        (y.clickCount, y.orderCount, y.payCount)
      }
    }(Ordering.Tuple3(Ordering.Long.reverse, Ordering.Long.reverse, Ordering.Long.reverse)).take(10)
    result.foreach(println)
    // 9、关闭上下文
    sc.stop()

  }

}


class ListAccumulator extends AccumulatorV2[UserVisitAction, ListBuffer[CategoryCountInfo]]{

  var list = new ListBuffer[CategoryCountInfo]

  override def isZero: Boolean = {
    list.isEmpty
  }

  //TODO 这里，按方法名理解应该是复制，但这样new一个新的，里面是没有值的
  override def copy(): AccumulatorV2[UserVisitAction, ListBuffer[CategoryCountInfo]] = {
//    new ListAccumulator
    val accumulator: ListAccumulator = new ListAccumulator
    accumulator.list = list.clone()
    accumulator
  }

  override def reset(): Unit = {
    list.clear()
  }

  override def add(ele: UserVisitAction): Unit = {
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
          list.append(CategoryCountInfo(ele.click_category_id.toString, 1, 0, 0))
        } else if (ele.order_category_ids != "null") {
          ele.order_category_ids.split(",").foreach {
            oid => list.append(CategoryCountInfo(oid, 0, 1, 0))
          }
        } else if (ele.pay_category_ids != "null") {
          ele.pay_category_ids.split(",").foreach {
            pid => list.append(CategoryCountInfo(pid, 0, 0, 1))
          }
        }
      }
      case _ => Unit
    }
  }

  override def merge(other: AccumulatorV2[UserVisitAction, ListBuffer[CategoryCountInfo]]): Unit = {
    list.appendAll(other.value)
  }

  override def value: ListBuffer[CategoryCountInfo] = {
    list
  }
}