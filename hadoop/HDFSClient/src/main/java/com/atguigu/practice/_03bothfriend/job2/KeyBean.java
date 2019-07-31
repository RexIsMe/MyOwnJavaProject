package com.atguigu.practice._03bothfriend.job2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Rex
 * @title: KeyBean
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3115:06
 */
public class KeyBean implements WritableComparable<KeyBean> {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int compareTo(KeyBean o) {
        return 0;
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(key);
    }

    public void readFields(DataInput in) throws IOException {
        this.key = in.readUTF();
    }
}
