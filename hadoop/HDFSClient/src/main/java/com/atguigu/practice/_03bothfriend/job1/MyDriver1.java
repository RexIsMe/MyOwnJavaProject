package com.atguigu.practice._03bothfriend.job1;

import com.atguigu.practice._03bothfriend.MyOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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
public class MyDriver1 {

    public static void main(String[] args) throws Exception{
        doIt(args);
    }

    public static void doIt(String[] args) throws Exception{

        Configuration configuration = new Configuration();
        configuration.set("test", "esttstststt======");
        Job job = new Job(configuration);

        job.setJarByClass(MyDriver1.class);
        job.setMapperClass(MyMapper1.class);
        job.setReducerClass(MyrReducer1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setOutputFormatClass(MyOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

    }

}
