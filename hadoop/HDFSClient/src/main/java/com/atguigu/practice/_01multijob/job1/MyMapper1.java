package com.atguigu.practice._01multijob.job1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyMapper1
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/316:21
 */
public class MyMapper1 extends Mapper<LongWritable, Text, Text, IntWritable> {

    private String name;
    private IntWritable v = new IntWritable(1);
    private Text k = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSplit inputSplit = (FileSplit)context.getInputSplit();
        name = inputSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] split = line.split(" ");
        for (int i = 0; i < split.length; i++) {
            String fs = split[i] + "------" + name;
            k.set(fs);
            context.write(k, v);
        }
    

    }
}
