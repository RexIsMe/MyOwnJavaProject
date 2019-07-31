package com.atguigu.practice._01multijob.job1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyReducer1
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/318:24
 */
public class MyReducer1 extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable v = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }

        v.set(sum);
        context.write(key, v);

    }
}
