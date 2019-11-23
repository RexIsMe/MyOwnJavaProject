//package com.atguigu.bigdata.spark.streaming
//
//import java.net.URI
//
//import kafka.common.TopicAndPartition
//import kafka.message.MessageAndMetadata
//import kafka.serializer.StringDecoder
//import org.apache.hadoop.conf.Configuration
//import org.apache.hadoop.fs.{FileSystem, Path}
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
//import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaCluster, KafkaUtils, OffsetRange}
//import org.apache.spark.streaming.kafka.KafkaCluster.Err
//import org.apache.spark.streaming.{Seconds, StreamingContext, StreamingContextState}
//
///**
//  * kafka动态分区支持
//  */
//object SparkStreaming10_Kafka_LowAPI {
//
//  def main(args: Array[String]): Unit = {
//
//
//    // 监听指定端口，获取数据，实现WordCount功能
//
//    val sparkConf = new SparkConf().setAppName("SparkStreaming10_Kafka_LowAPI").setMaster("local[2]")
//
//    // 配置优雅的关闭
//    sparkConf.set("spark.streaming.stopGracefullyOnShutdown", "true")
//
//    // TODO 创建上下文环境对象
//    val ssc = new StreamingContext(sparkConf, Seconds(3))
//
//    // 自己维护数据消费的偏移量
//
//    // group => topic => partition => offset
//
//
//    // kafka 参数
//    //kafka参数声明
//    val brokers = "linux1:9092,linux2:9092,linux3:9092"
//    val topic = "atguigu190513"
//    val group = "bigdata"
//    val deserialization = "org.apache.kafka.common.serialization.StringDeserializer"
//    val kafkaParams = Map(
//      "zookeeper.connect" -> "linux1:2181,linux2:2181,linux3:2181",
//      ConsumerConfig.GROUP_ID_CONFIG -> group,
//      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
//      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> deserialization,
//      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> deserialization
//    )
//    // 获取Kafka集群对象
//    val kafkaCluster = new KafkaCluster(kafkaParams)
//
//    var topicAndPartition2Offset: Map[TopicAndPartition, Long] = Map[TopicAndPartition, Long]()
//
//    // 获取主题的分区信息
//    val topicMetadataEither: Either[Err, Set[TopicAndPartition]] = kafkaCluster.getPartitions(Set(topic))
//
//    if (topicMetadataEither.isRight) {
//      // 主题存在分区信息
//      val topicAndPartitions: Set[TopicAndPartition] = topicMetadataEither.right.get
//
//      // 返回消费者组在指定分区的偏移量
//      val topicAndPartition2OffsetEither: Either[Err, Map[TopicAndPartition, Long]] =
//        kafkaCluster.getConsumerOffsets(group, topicAndPartitions)
//
//      if (topicAndPartition2OffsetEither.isLeft) {
//        // 消费者组从来就没有消费数据
//        topicAndPartitions.foreach {
//          topicAndPartition => topicAndPartition2Offset = topicAndPartition2Offset + (topicAndPartition -> 0)
//        }
//      } else {
//        val current: Map[TopicAndPartition, Long] = topicAndPartition2OffsetEither.right.get
//        topicAndPartition2Offset ++= current
//      }
//    }
//
//    val dStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, String](
//      ssc,
//      kafkaParams,
//      topicAndPartition2Offset,
//      (message: MessageAndMetadata[String, String]) => message.message()
//    )
//
//    dStream.print()
//
//    dStream.foreachRDD(
//      rdd=>{
//        var map: Map[TopicAndPartition, Long] = Map[TopicAndPartition, Long]()
//
//        // 将RDD转换为Kafka对应的RDD，
//        val hasOffsetRangs: HasOffsetRanges = rdd.asInstanceOf[HasOffsetRanges]
//
//        // 获取偏移量信息
//        val ranges: Array[OffsetRange] = hasOffsetRangs.offsetRanges
//        // 循环遍历每一个分区信息，更新偏移量
//        ranges.foreach(range => {
//          // 每个分区的最新的 offset
//          map += range.topicAndPartition() -> range.untilOffset
//        })
//        // 向kafka集群更新偏移量
//        kafkaCluster.setConsumerOffsets(group,map )
//      }
//    )
//
//
//
//    // TODO 让采集器启动执行
//    ssc.start()
//
//    // TODO Driver等待采集器的执行完毕
//    ssc.awaitTermination()
//
//
//  }
//}
