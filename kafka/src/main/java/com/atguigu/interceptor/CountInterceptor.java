package com.atguigu.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.ProducerInterceptors;
import org.apache.kafka.common.TopicPartition;

import java.util.List;

/**
 * @author Rex
 * @title: CountInterceptor
 * @projectName kafka
 * @description: TODO
 * @date 2019/8/148:08
 */
public class CountInterceptor extends ProducerInterceptors<String, String> {

    private Long successCounter;
    private Long errorCounter;

    public CountInterceptor(List<ProducerInterceptor<String, String>> producerInterceptors) {
        super(producerInterceptors);
    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return super.onSend(record);
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        super.onAcknowledgement(metadata, exception);

        // 统计成功和失败的次数
        if (exception == null) {
            successCounter++;
        } else {
            errorCounter++;
        }

    }

    @Override
    public void onSendError(ProducerRecord<String, String> record, TopicPartition interceptTopicPartition, Exception exception) {
        super.onSendError(record, interceptTopicPartition, exception);
    }

    @Override
    public void close() {

        System.out.println(successCounter);
        System.out.println(errorCounter);

        super.close();
    }
}
