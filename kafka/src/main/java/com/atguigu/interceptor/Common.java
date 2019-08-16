package com.atguigu.interceptor;

import com.atguigu.consumer.MyConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;

import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @author Rex
 * @title: Common
 * @projectName kafka
 * @description: TODO
 * @date 2019/8/148:14
 */
public class Common {

        public static void main(String[] args) throws Exception {
            // 1 设置配置信息
            Properties props = new Properties();
            props.put("bootstrap.servers", "hadoop102:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            List<String> interceptorChain = new ArrayList<>();
            interceptorChain.add("com.atguigu.interceptor.CountInterceptor");
            interceptorChain.add("com.atguigu.interceptor.TimeInterceptor");
            // 2 构建拦截链
//            List<String> interceptors = new ArrayList<>();
//            interceptors.add("com.atguigu.kafka.interceptor.TimeInterceptor"); 	interceptors.add("com.atguigu.kafka.interceptor.CounterInterceptor");
            props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorChain);

            String topic = "first";
            Producer<String, String> producer = new KafkaProducer<>(props);

            // 3 发送消息
            for (int i = 0; i < 10; i++) {

                ProducerRecord<String, String> record = new ProducerRecord<>(topic, "message" + i);
                producer.send(record);
            }

            // 4 一定要关闭producer，这样才会调用interceptor的close方法
            producer.close();
        }

}
