package com.atguigu.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author Rex
 * @title: Consumer
 * @projectName kafka
 * @description: TODO
 * @date 2019/8/1314:24
 */
public class MyConsumer {

    /**
     * 测试新增组内消费者是发生的rebalance
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        consumer("test-3", "test", "first", "first2");
    }


    /**
     * 测试组内多个consumer 和 多个组间的消息拉取情况
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Thread thread1 = new Thread(() -> {
            try {
                consumer("test-1", "test", "first", "first2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            try {
                consumer("test-2", "test", "first", "first2");
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            try {
                consumer("haha-1", "haha", "first", "first2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread3.start();
    }

    public static Properties getProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop102:9092");
        props.put("group.id", "test");
        //自动提交启用
        props.put("enable.auto.commit", "true");
        //自动提交时间间隔
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }


    public static void consumer(String label, String groupName, String... topics) throws Exception{
        Properties properties1 = getProperties();

        if (groupName != null){
            properties1.setProperty("group.id", groupName);
        }

        org.apache.kafka.clients.consumer.Consumer<String, String> consumer = new KafkaConsumer<>(properties1);
        consumer.subscribe(Arrays.asList(topics));

        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(1000L);
            Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
            while (iterator.hasNext()){
                ConsumerRecord<String, String> next = iterator.next();
                System.out.println(label + "=》" + properties1.getProperty("group.id") + ":" + next.topic() + ":" + next.partition() + ":" + next.offset() + ":" + next.key() +  " ->" + next.value());
            }
        }
    }

}
