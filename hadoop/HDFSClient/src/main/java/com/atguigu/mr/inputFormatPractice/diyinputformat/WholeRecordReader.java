package com.atguigu.mr.inputFormatPractice.diyinputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author Rex
 * @title: WholeRecordReader
 * @projectName HDFSclient
 * @description: 注意各方法的调用时机
 * @date 2019/7/2911:04
 */
public class WholeRecordReader extends RecordReader<Text, BytesWritable> {

    private Configuration configuration;
    private FileSplit split;

    private boolean isProgress = true;
    private BytesWritable value = new BytesWritable();
    private Text k = new Text();

    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        this.split = (FileSplit) split;
        configuration = context.getConfiguration();
    }

    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (isProgress) {

            // 1 定义缓存区
            byte[] contents = new byte[(int) split.getLength()];

            FileSystem fs = null;
            FSDataInputStream fis = null;

            try {
                // 2 获取文件系统
                fs = FileSystem.get(configuration);

                // 3 读取数据
                Path path = split.getPath();
                fis = fs.open(path);

                // 4 读取文件内容
                IOUtils.readFully(fis, contents, 0, contents.length);

                // 5 输出文件内容
                value.set(contents, 0, contents.length);

                // 6 获取文件路径及名称
                String name = split.getPath().toString();

                // 7 设置输出的key值
                k.set(name);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeStream(fis);
            }

            isProgress = false;

            return true;
        }

        return false;
    }

    public Text getCurrentKey() throws IOException, InterruptedException {
        return k;
    }

    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    public void close() throws IOException {

    }
}
