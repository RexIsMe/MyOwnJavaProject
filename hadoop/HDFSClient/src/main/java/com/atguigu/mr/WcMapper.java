package com.atguigu.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author Rex
 * @title: WcMapper
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2521:16
 */
public class WcMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

    private static final Logger LOG = Logger.getLogger(WcMapper.class);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        LOG.info("------------------------diy mapper------------------------");

        Text k = new Text();
        IntWritable v = new IntWritable(1);

        // 1 获取一行
        String line = value.toString();

        // 2 切割
        String[] words = line.split(" ");

        // 3 输出
        for (String word : words) {

            k.set(word);
            context.write(k, v);
        }

    }
}
