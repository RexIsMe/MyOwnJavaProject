package com.atguigu.practice._03bothfriend.job1;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Rex
 * @title: MyMapper1
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3112:21
 */
public class MyMapper1 extends Mapper<LongWritable, Text, Text, Text> {

    private Text k = new Text();
    private Text v = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split(":");

        String zhudong = split[0];

        if(StringUtils.isNotBlank(zhudong)){
            String[] split1 = split[1].split(",");
            for (int i = 0; i < split1.length; i++) {
                String s = split1[i];
                //被关注者
                k.set(s);
                //关注者
                v.set(zhudong);
                context.write(k, v);
            }
        }
    }
}
