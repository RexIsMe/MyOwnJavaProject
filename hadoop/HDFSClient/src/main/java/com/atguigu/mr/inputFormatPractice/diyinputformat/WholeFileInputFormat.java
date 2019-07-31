package com.atguigu.mr.inputFormatPractice.diyinputformat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * @author Rex
 * @title: WholeFileInputFormat
 * @projectName HDFSclient
 * @description: 自定义inputFormat,重写记录读取方法 以文件全路径为key,以对应的文件内容的二进制数据为value
 * @date 2019/7/2911:02
 */
public class WholeFileInputFormat extends FileInputFormat<Text, BytesWritable> {

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    /**
     * 这里主要是重写了createRecordReader方法
     * @param split
     * @param context
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        WholeRecordReader recordReader = new WholeRecordReader();
        recordReader.initialize(split, context);

        return recordReader;
    }
}
