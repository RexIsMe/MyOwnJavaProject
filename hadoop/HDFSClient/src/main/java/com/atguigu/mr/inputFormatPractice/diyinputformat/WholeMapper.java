package com.atguigu.mr.inputFormatPractice.diyinputformat;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Rex
 * @title: WholeMapper
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2911:13
 */
public class WholeMapper extends Mapper<Text, BytesWritable, Text, BytesWritable> {

    @Override
    protected void map(Text key, BytesWritable value, Context context) throws IOException, InterruptedException {
        context.write(key, value);
    }
}
