package com.atguigu.mr.diyserialize;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Rex
 * @title: StatisticReducer
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2618:51
 */
public class StatisticReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    FlowBean flowBean = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        Long upFlow = 0L;
        Long downFlow = 0L;
        //将key相同的flowBean的各个属性值对应相加
        for (FlowBean flowBean : values) {
            upFlow += flowBean.getUpFlow();
            downFlow += flowBean.getDownFlow();
        }

        flowBean.setUpFlow(upFlow);
        flowBean.setDownFlow(downFlow);
        flowBean.setSumFlow(upFlow + downFlow);

        context.write(key, flowBean);

    }
}
