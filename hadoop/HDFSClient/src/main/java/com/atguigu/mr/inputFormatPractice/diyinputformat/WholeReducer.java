package com.atguigu.mr.inputFormatPractice.diyinputformat;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Rex
 * @title: WholeReducer
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2911:14
 */
public class WholeReducer extends Reducer<Text, BytesWritable, Text, BytesWritable> {

    @Override
    protected void reduce(Text key, Iterable<BytesWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key, values.iterator().next());
    }
}
