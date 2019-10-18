package com.atguigu.hive.etlpractice;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.counters.GenericCounter;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyMapper
 * @projectName HiveUDF
 * @description: 过滤video.txt中不满足条件的数据
 * @date 2019/8/713:50
 */
public class MyMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private StringBuilder stringBuilder = new StringBuilder();
    private Text text = new Text();

    private Counter total;
    private Counter pass;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        total = context.getCounter("ETL", "total");
        pass = context.getCounter("ETL", "pass");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        stringBuilder.setLength(0);
        total.increment(1);

        //1、根据“\t”切割每行数据
        String[] split = value.toString().split("\t");

        //2、判断数据是否正确
        if (split.length >= 9) {
            pass.increment(1);

            //3、转换数据格式，并拼接成新的行输出
            for (int i = 0; i < split.length; i++) {
                if (i == split.length - 1) {
                    stringBuilder.append(split[i]);
                } else if (i < 9){
                    if(i == 3){
                        split[i] = split[i].replaceAll(" ", "");
                    }
                    stringBuilder.append(split[i]);
                    stringBuilder.append("\t");
                } else {
                    stringBuilder.append(split[i]);
                    stringBuilder.append("&");
                }
            }


            text.set(stringBuilder.toString());
            context.write(text, NullWritable.get());
        }

    }
}
