package com.atguigu.bigdata.spark.streaming

/**
  * @title: CloseGentlily
  * @projectName spark-190513
  * @description: TODO
  * @author Rex
  * @date 2019/10/2115:57
  */
object CloseGentlily {

  def main(args: Array[String]): Unit = {


    /*sparkConf.set("spark.streaming.stopGracefullyOnShutdown", "true")

    // 启动新的线程，希望在特殊的场合关闭SparkStreaming
    new Thread(new Runnable {
      override def run(): Unit = {

        while ( true ) {
          try {
            Thread.sleep(5000)
          } catch {
            case ex : Exception => println(ex)
          }

          // 监控HDFS文件的变化
          val fs: FileSystem = FileSystem.get(new URI("hdfs://linux1:9000"), new Configuration(), "root")

          val state: StreamingContextState = context.getState()
          // 如果环境对象处于活动状态，可以进行关闭操作
          if ( state == StreamingContextState.ACTIVE ) {

            // 判断路径是否存在
            val flg: Boolean = fs.exists(new Path("hdfs://linux1:9000/stopSpark2"))
            if ( flg ) {
              context.stop(true, true)
              System.exit(0)
            }

          }
        }

      }
    }).start()*/

  }



}
