package com.atguigu.mr.diywordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author Rex
 * @title: WcReducer
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2521:16
 */
public class WcReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private static final Logger LOG = Logger.getLogger(WcReducer.class);

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        LOG.info("------------------------diy reducer------------------------");

        int sum;
        IntWritable v = new IntWritable();

        // 1 累加求和
        sum = 0;
        for (IntWritable count : values) {
            sum += count.get();
        }

        // 2 输出
        v.set(sum);
        context.write(key,v);
    }
}
