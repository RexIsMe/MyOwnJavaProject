package com.atguigu.practice._02topn;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Rex
 * @title: StatisticReducer
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2618:51
 */
public class StatisticReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

    // 定义一个TreeMap作为存储数据的容器（天然按key排序）
    public static TreeMap<FlowBean, Text> flowMap;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        flowMap = new TreeMap<FlowBean, Text>();

        super.setup(context);
    }

    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


        for (Text value : values) {

            // 1 向treeMap集合中添加数据
            flowMap.put(new FlowBean(key.getUpFlow(),key.getDownFlow()), new Text(value));
            /**
             * flowMap.put(key, new Text(value));
             * 这种写法会导致flowMap中只有一个元素，
             * 原因是这里的key只是一个引用，对象是同一个，但是指向的引用不同-》值不同
              */

            // 2 限制TreeMap数据量，超过10条就删除掉流量最小的一条数据
            if (flowMap.size() > 10) {
                // flowMap.remove(flowMap.firstKey());
                System.out.println("%%%%%%%%%%"+flowMap.lastKey()+"%%%%%%%%%%%");
                flowMap.remove(flowMap.lastKey());
            }

        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

        System.out.println("=====================" + flowMap.size() + "==" + this + "=====================");

        Iterator<Map.Entry<FlowBean, Text>> iterator = flowMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<FlowBean, Text> next = iterator.next();
            context.write(next.getValue(), next.getKey());
        }

        super.cleanup(context);
    }
}
