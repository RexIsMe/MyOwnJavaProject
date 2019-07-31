package com.atguigu.practice._03bothfriend.job2;

import com.atguigu.practice._03bothfriend.job1.MyMapper1;
import com.atguigu.practice._03bothfriend.job1.MyrReducer1;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author Rex
 * @title: MyDriver1
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3112:35
 */
public class MyDriver2 {

    public static void main(String[] args) throws Exception{
        doIt(args);
    }

    public static void doIt(String[] args) throws Exception{

        Configuration configuration = new Configuration();
        configuration.set("testKey", "testValue");
        Job job = new Job(configuration);

        job.setJarByClass(MyDriver2.class);
        job.setMapperClass(MyMapper2.class);
        job.setReducerClass(MyReducer2.class);

        job.setMapOutputKeyClass(KeyBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setGroupingComparatorClass(MyComparator.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

    }

}
