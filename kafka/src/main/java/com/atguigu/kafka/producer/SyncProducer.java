package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Rex
 * @title: SyncProducer
 * @projectName kafka
 * @description: TODO
 * @date 2019/8/1310:25
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.load(new FileInputStream("D:\\workSpace\\myOwn\\kafka\\target\\classes\\kafka_producer.properties"));
//        props.put("bootstrap.servers", "hadoop102:9092");//kafka集群，broker-list
//        props.put("acks", "all");
//        props.put("retries", 1);//重试次数
//        props.put("batch.size", 16384);//批次大小
//        props.put("linger.ms", 1);//等待时间
//        props.put("buffer.memory", 33554432);//RecordAccumulator缓冲区大小
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        for (int i = 0; i < 10; i++) {
            Future<RecordMetadata> future =
                    producer.send(new ProducerRecord<String, String>("first", "test" + i));

            RecordMetadata recordMetadata = future.get();
            System.out.println(recordMetadata.toString());

        }

        System.out.println("send over");

        producer.close();

    }
}
