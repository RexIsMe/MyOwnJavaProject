package com.atguigu.practice._03bothfriend.job2;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyMapper2
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3113:54
 */
public class MyMapper2 extends Mapper<LongWritable, Text, KeyBean, Text> {

    private String fileName;
    private KeyBean k = new KeyBean();
    private Text v = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        InputSplit inputSplit = context.getInputSplit();
        FileSplit inputSplit1 = (FileSplit) inputSplit;
        fileName = inputSplit1.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        if(StringUtils.isNotBlank(value.toString())){
            k.setKey(fileName);
            System.out.println("====================="+k+value+"====================");
            v.set(fileName+ "=" +value);
            context.write(k, v);
        }

    }
}
