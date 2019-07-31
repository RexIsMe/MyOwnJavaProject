package com.atguigu.practice._01multijob;

import org.apache.hadoop.io.IntWritable;
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
 * @date 2019/7/3110:04
 */
public class MyOutputFormat extends FileOutputFormat<Text, IntWritable> {

    public RecordWriter<Text, IntWritable> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        return new MyRecordWriter(job);
    }

}
