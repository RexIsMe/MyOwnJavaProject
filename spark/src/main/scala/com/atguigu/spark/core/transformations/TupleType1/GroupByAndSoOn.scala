package com.atguigu.spark.core.transformations.TupleType1

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * @title: PartitionBy
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1015:45
  */
object PartitionBy {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("myTest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[(String, Int)] = sc.parallelize(List(("female",1),("male",5),("female",5),("male",2)))

    /**
      * GroupByKey
      * 因为groupByKey只有一个分区间的聚合处理，所以性能较低，不建议使用
      */
    val groupBy: RDD[(String, Iterable[Int])] = value.groupByKey()
    val result1: RDD[(String, Int)] = groupBy.map(x => (x._1, x._2.sum))
    result1.collect().foreach(println)

    /**
      * ReduceByKey
      * 与groupByKey相比，会先做一个分区内的聚合（预聚合），再做分区间的聚合
      * 所有性能较高，但是也可能导致分区内的OOM
      */
    val result2: RDD[(String, Int)] = value.reduceByKey(_+_)
    result2.collect().foreach(println)

    /**
      * AggregateByKey
      * 执行流程和ReduceByKey相同，
      * 区别：
      * 1、返回值类型可以不同，更加灵活
      * 2、分区内和分区间的处理逻辑用两个算子表示，可以不同
      */
    val result3: RDD[(String, Int)] = value.aggregateByKey(0)(_+_, _+_)
    result3.collect().foreach(println)

    /**
      * foldByKey
      * 当
      * 1、返回值类型不变
      * 2、aggregateByKey的分区内算子和分区间算子相同时
      * 可以用foldByKey替换
      */
    val result4: RDD[(String, Int)] = value.foldByKey(0)(_+_)
    result4.collect().foreach(println)

    /**
      * TODO combineByKey
      * 参数描述:
      * •createCombiner: combineByKey会遍历分区中的每个key-value对. 如果第一次碰到这个key, 则调用createCombiner函数,传入value, 得到一个C类型的值.(如果不是第一次碰到这个 key, 则不会调用这个方法)
      * •mergeValue: 如果不是第一个遇到这个key, 则调用这个函数进行合并操作. 分区内合并
      * •mergeCombiners 跨分区合并相同的key的值(C). 跨分区合并
      */
    val result5: RDD[(String, Int)] = value.combineByKey(x => x, (x:Int, y:Int) => x+y, (x:Int, y:Int) => x+y)
    result5.collect().foreach(println)

    sc.stop()
  }

}
