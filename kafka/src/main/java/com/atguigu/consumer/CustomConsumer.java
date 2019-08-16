package com.atguigu.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.io.*;
import java.util.*;

public class CustomConsumer {

    //目前消费的Offset的临时存储
    private static Map<TopicPartition, Long> offset = new HashMap<TopicPartition, Long>();


    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop102:9092");//Kafka集群
        props.put("group.id", "test");//消费者组，只要group.id相同，就属于同一个消费者组
        props.put("enable.auto.commit", "false");//关闭自动提交offset
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //消费者就已经订阅到我们的Topic
        consumer.subscribe(Collections.singleton("first"), new ConsumerRebalanceListener() {

            /**
             * 在再平衡执行之前，我们要做的事
             * @param partitions
             */
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                //把Offset提交到我们的自定义介质中去
                commitOffset(offset);
            }

            /**
             * 再平衡之后，我们要做的事
             * @param partitions
             */
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                //1. 获取新的Offset
                getOffset(offset);
                //2. 遍历我们新分到的partition，设置开始消费数据的位置
                for (TopicPartition partition : partitions) {
                    Long oset = offset.get(partition);
                    if (oset == null) {
                        oset = 0L;
                    }
                    consumer.seek(partition, oset);
                }
            }
        });

        while (true) {
            //拉取数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            //将消费和提交原子化，保证精确消费
            {
                //消费数据
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.value());
                    //更新我们临时的Map
                    offset.put(new TopicPartition(record.topic(), record.partition()), record.offset());
                }
                commitOffset(offset);
            }

        }

    }

    /**
     * 根据我们的自定义介质获取Offset
     * @param offset
     */
    private static void getOffset(Map<TopicPartition, Long> offset) {
        //从介质中读取Offset
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("d:/offset"));
            offset = (Map<TopicPartition, Long>) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据我们目前消费到的数据，将Offset保存到我们自定义的存储地点
     * @param offset
     */

    public static void commitOffset(Map<TopicPartition, Long> offset) {
        //Map中有数据，保存进我们的自定义介质
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("d:/offset"));
            outputStream.writeObject(offset);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
