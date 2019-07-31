package com.atguigu.practice._01multijob.job2;

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
public class MyReducer2 extends Reducer<Text, Text, Text, Text> {

    private Text v = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        StringBuilder sum = new StringBuilder();
        for (Text value : values) {
            sum.append(value.toString());
            sum.append(" ");
        }

        v.set(String.valueOf(sum));

        context.write(key, v);

    }
}
