package com.atguigu.flume;


import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;
import java.util.Map;

/**
 * @author Rex
 * @title: MyInterceptor
 * @projectName flume
 * @description: TODO
 * @date 2019/8/1014:42
 */
public class MyInterceptor implements Interceptor {
    public void initialize() {

    }

    public Event intercept(Event event) {

        Map<String, String> headers = event.getHeaders();

        byte[] body = event.getBody();
        String value;
        if(body[0] >= '0' && body[0] <= '9'){
            value = "number";
        } else {
            value = "nan";
        }
        headers.put("dataType", value);

        return event;
    }

    public List<Event> intercept(List<Event> list) {

        for (int i = 0; i < list.size(); i++) {
            intercept(list.get(i));
        }

        return list;
    }

    public void close() {

    }

    public static class MyBuilder implements Builder{

        public Interceptor build() {
            return new MyInterceptor();
        }

        public void configure(Context context) {

        }
    }
}
