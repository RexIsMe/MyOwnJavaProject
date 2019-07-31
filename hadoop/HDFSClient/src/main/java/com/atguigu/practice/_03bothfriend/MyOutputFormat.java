package com.atguigu.practice._03bothfriend;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyOutputFormat
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3116:29
 */
public class MyOutputFormat extends FileOutputFormat<Text, Text> {


    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        return new MyRecordWriter(job);
    }
}
