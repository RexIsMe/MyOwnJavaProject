package com.atguigu.mr.partition;

import com.atguigu.mr.diyserialize.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author Rex
 * @title: DiyPartitioner
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2911:55
 */
public class DiyPartitioner extends Partitioner<Text, FlowBean> {

    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        // 1 获取电话号码的前三位
        String preNum = text.toString().substring(0, 3);

        int partition = 4;

        // 2 判断是哪个省
        if ("136".equals(preNum)) {
            partition = 0;
        }else if ("137".equals(preNum)) {
            partition = 1;
        }else if ("138".equals(preNum)) {
            partition = 2;
        }else if ("139".equals(preNum)) {
            partition = 3;
        }

        return partition;
    }

}
