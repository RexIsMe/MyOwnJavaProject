package com.atguigu.practice._02topn;

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
public class StatisticMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

    Text phoneNumText = new Text();
    FlowBean flowBean = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //处理接收的整行数据，拿到我们想要的
        String line = value.toString();
        String[] split = line.split("\t");

        String phoneNum = split[0];
        String upFlow = split[1];
        String downFlow = split[2];
        String sumFlow = split[3];

        phoneNumText.set(phoneNum);
        flowBean.setUpFlow(Long.valueOf(upFlow));
        flowBean.setDownFlow(Long.valueOf(downFlow));
        flowBean.setSumFlow(Long.valueOf(sumFlow));

        System.out.println("====================" + phoneNumText.toString() + flowBean + "======================");

        context.write(flowBean, phoneNumText);
    }
}
