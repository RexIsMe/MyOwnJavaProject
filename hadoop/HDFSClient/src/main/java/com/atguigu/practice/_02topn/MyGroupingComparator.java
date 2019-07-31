package com.atguigu.practice._02topn;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author Rex
 * @title: MyGroupingComparator
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/316:57
 */
public class MyGroupingComparator extends WritableComparator {

    public MyGroupingComparator() {
        super(FlowBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        FlowBean fa = (FlowBean) a;
        FlowBean fb = (FlowBean) b;

        return Integer.valueOf(Long.valueOf(fa.getSumFlow() - fb.getSumFlow()).toString());
    }
}
