package com.atguigu.flume;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

/**
 * @author Rex
 * @title: MySink
 * @projectName flume
 * @description: TODO
 * @date 2019/8/1014:43
 */
public class MySink extends AbstractSink implements Configurable {

    private String prefix;
    private String suffix;

    /**
     * 拉取channel中的数据，并负责事务
     * @return
     * @throws EventDeliveryException
     */
    public Status process() throws EventDeliveryException {
        Status status;
        Channel channel = this.getChannel();
        Transaction transaction = channel.getTransaction();
        try {
            transaction.begin();

            Event event;

            while (true) {
                event = channel.take();
                if(event != null){
                    break;
                }
            }

            System.out.println((prefix + new String(event.getBody()) + suffix).getBytes());

            transaction.commit();
            status = Status.READY;
        } catch (Exception e) {
            transaction.rollback();
            status = Status.BACKOFF;
            System.out.println(e.getMessage());
        } finally {
            if(transaction != null){
                transaction.close();
            }
        }

        return status;
    }

    /**
     * 读取上下文中的配置
     * @param context
     */
    public void configure(Context context) {
        prefix = context.getString("prefix", "pre:");
        suffix = context.getString("prefix", ":suf");
    }
}
