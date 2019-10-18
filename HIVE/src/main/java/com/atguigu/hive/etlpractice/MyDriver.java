package com.atguigu.hive.etlpractice;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import javax.xml.soap.Text;

/**
 * @author Rex
 * @title: MyDriver
 * @projectName HiveUDF
 * @description: TODO
 * @date 2019/8/713:50
 */
public class MyDriver {

    public static void main(String[] args) throws Exception{

        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(MyDriver.class);

        job.setMapperClass(MyMapper.class);
        job.setNumReduceTasks(0);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);
    }

}
