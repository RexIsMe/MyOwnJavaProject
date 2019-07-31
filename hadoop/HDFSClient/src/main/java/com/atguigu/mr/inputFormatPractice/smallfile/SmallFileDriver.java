package com.atguigu.mr.inputFormatPractice.smallfile;

import com.atguigu.mr.diywordcount.WcMapper;
import com.atguigu.mr.diywordcount.WcReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author Rex
 * @title: SmallFileDriver
 * @projectName HDFSclient
 * @description: 小文件分片设置
 * @date 2019/7/2910:35
 */
public class SmallFileDriver {

    public static void main(String[] args) throws Exception{

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        /**
         * 设置主要步骤具体逻辑执行类
         */
        job.setJarByClass(SmallFileDriver.class);
        job.setMapperClass(WcMapper.class);
        job.setReducerClass(WcReducer.class);

        /**
         * 设置map阶段、mapreduce最终输出的key\value类型
         */
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, 1024 * 1024 * 4);

        /**
         * 设置输入、输出文件路径
         */
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

}
