package com.atguigu.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming06_Transform {

  def main(args: Array[String]): Unit = {

    // 监听指定端口，获取数据，实现WordCount功能

    val sparkConf = new SparkConf().setAppName("SparkStreaming05_State").setMaster("local[2]")

    // TODO 创建上下文环境对象
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // TODO 获取离散化流
    val socketDStream: ReceiverInputDStream[String] = ssc.socketTextStream("linux1", 9999)

    // Coding => Driver（1）
    socketDStream.flatMap{
      line => {
        // Coding => Executor（M）
        line.split(" ")
      }
    }

    // 1. Coding => Driver (1)
    socketDStream.transform{
      rdd => {
        // 3. Coding => Driver (N)
        rdd.flatMap{
          // 2. Coding => Executor（M）
          _.split(" ")
        }
      }
    }

    // TODO 让采集器启动执行
    ssc.start()

    // TODO Driver等待采集器的执行完毕
    ssc.awaitTermination()
  }
}
