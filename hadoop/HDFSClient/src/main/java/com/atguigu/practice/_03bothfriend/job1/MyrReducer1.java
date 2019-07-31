package com.atguigu.practice._03bothfriend.job1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyrReducer1
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3112:29
 */
public class MyrReducer1 extends Reducer<Text, Text, Text, Text> {

    private Text k = new Text();
    private Text v = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        StringBuilder sum = new StringBuilder();
        for (Text value : values) {
            sum.append(value.toString() + ",");
        }

        int length = sum.length();
        String result = "";
        if (length != 0) {
            result = sum.subSequence(0, length - 1).toString();
        }

        k.set(result);
        v.set(key);

        context.write(k, v);
    }
}
