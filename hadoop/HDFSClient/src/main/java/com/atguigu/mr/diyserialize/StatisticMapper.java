package com.atguigu.mr.diyserialize;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Rex
 * @title: StatisticMapper
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2618:37
 */
public class StatisticMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    Text phoneNumText = new Text();
    FlowBean flowBean = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //处理接收的整行数据，拿到我们想要的
        String line = value.toString();
        String[] split = line.split("\t");

        int length = split.length;
        String phoneNum = split[1];
        String upFlow = split[length -3];
        String downFlow = split[length -2];

        phoneNumText.set(phoneNum);
        flowBean.setUpFlow(Long.valueOf(upFlow));
        flowBean.setDownFlow(Long.valueOf(downFlow));
        context.write(phoneNumText, flowBean);
    }
}
