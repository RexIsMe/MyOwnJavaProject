package com.atguigu.mr.outputformat;

import com.atguigu.mr.diyserialize.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author Rex
 * @title: FilterOutputFormat
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2914:21
 */
public class FilterOutputFormat extends FileOutputFormat<Text, FlowBean> {


    public RecordWriter<Text, FlowBean> getRecordWriter(TaskAttemptContext job) {
        // 创建一个RecordWriter
        return new FilterRecordWriter(job);
    }
}
