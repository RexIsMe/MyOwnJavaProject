package com.atguigu.mr.useallmodule;

import org.apache.hadoop.mapreduce.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Rex
 * @title: MyInputFormat
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/287:37
 */
public class MyInputFormat extends InputFormat {
    public List<InputSplit> getSplits(JobContext context) throws IOException, InterruptedException {
        return null;
    }

    public RecordReader createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return null;
    }
}
