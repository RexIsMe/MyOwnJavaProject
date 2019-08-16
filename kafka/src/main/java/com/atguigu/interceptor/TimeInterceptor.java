package com.atguigu.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.ProducerInterceptors;
import org.apache.kafka.common.TopicPartition;

import java.util.List;

/**
 * @author Rex
 * @title: TimeInterceptor
 * @projectName kafka
 * @description: TODO
 * @date 2019/8/147:50
 */
public class TimeInterceptor extends ProducerInterceptors<String, String> {


    public TimeInterceptor(List<ProducerInterceptor<String, String>> producerInterceptors) {
        super(producerInterceptors);
    }


    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {

        String value = record.value();
        String newValue = System.currentTimeMillis() + "," + value;

        ProducerRecord<String, String> newPr = new ProducerRecord<String, String>(
                record.topic(),
                record.partition(),
                record.key(),
                newValue,
                record.headers()
        );

        return newPr;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        super.onAcknowledgement(metadata, exception);
    }

    @Override
    public void onSendError(ProducerRecord<String, String> record, TopicPartition interceptTopicPartition, Exception exception) {
        super.onSendError(record, interceptTopicPartition, exception);
    }

    @Override
    public void close() {
        super.close();
    }
}
