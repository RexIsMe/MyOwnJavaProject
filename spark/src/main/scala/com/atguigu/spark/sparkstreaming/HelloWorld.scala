package com.atguigu.spark.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @title: HelloWorld
  * @projectName spark
  * @description: TODO
  * @author Rex
  * @date 2019/10/1114:45
  */
object HelloWorld {

  def main(args: Array[String]): Unit = {

     val conf: SparkConf = new SparkConf().setAppName("hw").setMaster("local[*]")
     val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    val unit: ReceiverInputDStream[String] = ssc.socketTextStream("warehouse102", 9999)

    val value1: DStream[String] = unit.flatMap(line => line.split(" "))
    val value2: DStream[(String, Int)] = value1.map(x => (x, 1))

    val value3: DStream[(String, Int)] = value2.reduceByKey(_+_)

    value3.print()


    println("111111")
    ssc.start()
    ssc.awaitTermination()

  }

}
