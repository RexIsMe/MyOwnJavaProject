package com.atguigu.practice._03bothfriend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyRecordWriter
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3116:31
 */
public class MyRecordWriter extends RecordWriter<Text, Text> {

    private FSDataOutputStream out = null;

    public MyRecordWriter(TaskAttemptContext job) {

        // 1 获取文件系统
        FileSystem fs;

        try {
            Configuration configuration = job.getConfiguration();
            fs = FileSystem.get(configuration);

            System.out.println(configuration.get("test"));

            // 2 创建输出文件路径
            Path atguiguPath = new Path("C:\\Users\\99686\\Desktop\\file\\practice\\practice\\3\\txt/result1.txt");

            // 3 创建输出流
            out = fs.create(atguiguPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(Text key, Text value) throws IOException, InterruptedException {
        String s = (key + "\t" + value + "\r\n").toString();
        out.write(s.getBytes());
    }

    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(out);
    }
}
