package com.atguigu.practice._03bothfriend.job2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author Rex
 * @title: MyComparator
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3115:10
 */
public class MyComparator extends WritableComparator {

    public MyComparator() {
        super(KeyBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        return 0;
    }
}
