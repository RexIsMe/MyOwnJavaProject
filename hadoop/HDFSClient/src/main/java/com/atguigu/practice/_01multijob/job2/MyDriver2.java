package com.atguigu.practice._01multijob.job2;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * @author Rex
 * @title: MyDriver1
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/318:27
 */
public class MyDriver2 {

    public static void main(String[] args) throws Exception {
        doIt(args);
    }

    public static void doIt(String[] args) throws Exception{
        JobConf configuration = new JobConf();
        Job job = new Job(configuration);

        job.setJarByClass(MyDriver2.class);
        job.setMapperClass(MyMapper2.class);
        job.setReducerClass(MyReducer2.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

    }

}
