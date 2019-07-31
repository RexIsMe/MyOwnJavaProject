package com.atguigu.practice._02topn;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Rex
 * @title: FlowBean
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2618:39
 */
public class FlowBean implements WritableComparable<FlowBean> {

    private Long upFlow;
    private Long downFlow;
    private Long sumFlow;

    public FlowBean() {
    }

    public FlowBean(Long upFlow, Long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    public Long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Long upFlow) {
        this.upFlow = upFlow;
    }

    public Long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Long downFlow) {
        this.downFlow = downFlow;
    }

    public Long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(Long sumFlow) {
        this.sumFlow = sumFlow;
    }

    public void set(long downFlow2, long upFlow2) {
        downFlow = downFlow2;
        upFlow = upFlow2;
        sumFlow = downFlow2 + upFlow2;
    }

    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    public void readFields(DataInput in) throws IOException {
        this.upFlow = in.readLong();
        this.downFlow = in.readLong();
        this.sumFlow = in.readLong();
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow ==  null ? 0L : sumFlow);
    }

    public int compareTo(FlowBean bean) {
        int result;

        if (this.sumFlow > bean.getSumFlow()) {
            result = -1;
        }else if (this.sumFlow < bean.getSumFlow()) {
            result = 1;
        }else {
            result = 0;
        }

        return result;
    }
}
