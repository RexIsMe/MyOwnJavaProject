package com.atguigu.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class AsyncCommitConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop102:9092");
        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        org.apache.kafka.clients.consumer.Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Collections.singleton("first"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            consumer.commitAsync(new OffsetCommitCallback() {
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                    System.out.println("提交完成");
                }
            });


            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }


        }

    }
}
