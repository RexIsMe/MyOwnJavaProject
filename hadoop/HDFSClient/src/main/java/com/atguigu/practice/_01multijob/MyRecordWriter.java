package com.atguigu.practice._01multijob;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyRecordWriter
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3110:07
 */
public class MyRecordWriter extends RecordWriter<Text, IntWritable> {


    private FSDataOutputStream atguiguOut = null;

    public MyRecordWriter(TaskAttemptContext job) {

        // 1 获取文件系统
        FileSystem fs;

        try {
            fs = FileSystem.get(job.getConfiguration());

            // 2 创建输出文件路径
            Path atguiguPath = new Path("C:\\Users\\99686\\Desktop\\file\\practice\\practice\\1/result1.txt");

            // 3 创建输出流
            atguiguOut = fs.create(atguiguPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(Text key, IntWritable value) throws IOException, InterruptedException {
        atguiguOut.write((key.toString() + " " + value.toString() + "\r\n").getBytes());
    }

    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(atguiguOut);
    }
}
