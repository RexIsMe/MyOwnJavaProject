package com.atguigu.flume;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Rex
 * @title: MySource
 * @projectName flume
 * @description: TODO
 * @date 2019/8/1014:42
 */
public class MySource extends AbstractSource implements Configurable, PollableSource {

    private String prefix;
    private String suffix;
    private Status status;
    private Long delay;
    private Event event = new SimpleEvent();

    public Status process() throws EventDeliveryException {
        try {
            status = null;
            ChannelProcessor channelProcessor = this.getChannelProcessor();
            String intStr = String.valueOf(new Random().nextInt());
            String value = intStr.substring(0, Math.min(10, intStr.length()));
            String str = prefix + value + suffix;
            event.setBody(str.getBytes());
            event.setHeaders(new HashMap<String, String>());
            channelProcessor.processEvent(event);
            status = Status.READY;

            Thread.sleep(delay);
        } catch (RuntimeException | InterruptedException e) {
            status = Status.BACKOFF;
            System.out.println(e.getMessage());
        }
        return status;
    }

    /**
     * 给出数据失败后，下次再给数据增加等待时间
     * @return
     */
    public long getBackOffSleepIncrement() {
        return 1 * 1000L;
    }

    /**
     * 给出数据失败后下次再给出数据的最大等待时间间隔
     * @return
     */
    public long getMaxBackOffSleepInterval() {
        return 20 * 1000L;
    }

    /**
     * 获取启动flume时的上下文配置
     * @param context
     */
    public void configure(Context context) {
        //看看里面都是什么
        ImmutableMap<String, String> parameters = context.getParameters();
        int size = parameters.size();
        if(size < 100){
            UnmodifiableIterator<String> iterator = parameters.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                System.out.println("【key:" + next + "value:" + parameters.get(next) + "】");
            }
        }

        prefix = context.getString("prefix");
        suffix = context.getString("suffix");
        delay = context.getLong("delay");

    }
}
