package com.atguigu.practice._01multijob.job2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
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
public class MyMapper2 extends Mapper<LongWritable, Text, Text, Text> {

    private Text k = new Text();
    private Text v = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] split = line.split("------");

        String word = split[0];
        k.set(word);

        String rest = split[1];
        String[] split1 = rest.split(" ");
        String fileName = split1[0];
        String num = split1[1];
        v.set(fileName + "--->" + num);

        context.write(k,v);
    }
}
