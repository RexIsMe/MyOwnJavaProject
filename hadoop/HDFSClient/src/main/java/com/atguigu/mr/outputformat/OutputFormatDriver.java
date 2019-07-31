package com.atguigu.mr.outputformat;


import com.atguigu.mr.diyserialize.FlowBean;
import com.atguigu.mr.diyserialize.StatisticMapper;
import com.atguigu.mr.diyserialize.StatisticReducer;
import com.atguigu.mr.partition.DiyPartitioner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author Rex
 * @title: StatisticDriver
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2619:01
 */
public class OutputFormatDriver {

    public static void main(String[] args) throws Exception{

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(OutputFormatDriver.class);
        job.setMapperClass(StatisticMapper.class);
        job.setReducerClass(StatisticReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 8 指定自定义数据分区
//        job.setPartitionerClass(DiyPartitioner.class);
//
//        // 9 同时指定相应数量的reduce task
//        job.setNumReduceTasks(5);

        /**
         * “分区” 和 “结果文件输出到不同文件”不能同时操作
         */
        job.setOutputFormatClass(FilterOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0  : 1);
    }

}
