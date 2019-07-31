package com.atguigu.mr.outputformat;

import com.atguigu.mr.diyserialize.FlowBean;
import com.atguigu.utils.LogUtil;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author Rex
 * @title: FilterRecordWriter
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2914:07
 */
public class FilterRecordWriter extends RecordWriter<Text, FlowBean> {


    FSDataOutputStream part1 = null;
    FSDataOutputStream part2 = null;
    FSDataOutputStream part3 = null;
    FSDataOutputStream part4 = null;
    FSDataOutputStream part5 = null;

    public FilterRecordWriter(TaskAttemptContext job) {

        // 1 获取文件系统
        FileSystem fs;

        try {
            fs = FileSystem.get(job.getConfiguration());

            // 2 创建输出文件路径
            Path path1 = new Path("C:\\Users\\99686\\Desktop\\file\\practice\\output\\outputformat\\part1\\part1.log");
            Path path2 = new Path("C:\\Users\\99686\\Desktop\\file\\practice\\output\\outputformat\\part1\\part2.log");
            Path path3 = new Path("C:\\Users\\99686\\Desktop\\file\\practice\\output\\outputformat\\part1\\part3.log");
            Path path4 = new Path("C:\\Users\\99686\\Desktop\\file\\practice\\output\\outputformat\\part1\\part4.log");
            Path path5 = new Path("C:\\Users\\99686\\Desktop\\file\\practice\\output\\outputformat\\part1\\part5.log");

            // 3 创建输出流
            part1 = fs.create(path1);
            part2 = fs.create(path2);
            part3 = fs.create(path3);
            part4 = fs.create(path4);
            part5 = fs.create(path5);

            part1.write("fuck\r\n".getBytes());
            part2.write("fuck\r\n".getBytes());
            part3.write("fuck\r\n".getBytes());
            part4.write("fuck\r\n".getBytes());
            part5.write("fuck\r\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, FlowBean value) throws IOException, InterruptedException {

        // 判断是否包含“atguigu”输出到不同文件
        FSDataOutputStream part = part5;
        String preNum = key.toString().substring(0, 3);
        if ("136".equals(preNum)) {
            part = part1;
        }else if ("137".equals(preNum)) {
            part = part2;
        }else if ("138".equals(preNum)) {
            part = part3;
        }else if ("139".equals(preNum)) {
            part = part4;
        }

        LogUtil.getLog(this.getClass()).info("============="+part+"===============" + key);
        part.write(getBytes(key, value));

//        String preNum = key.toString().substring(0, 3);
//        if ("136".equals(preNum)) {
//            part1.write(getBytes(key, value));
//        }else if ("137".equals(preNum)) {
//            part2.write(getBytes(key, value));
//        }else if ("138".equals(preNum)) {
//            part3.write(getBytes(key, value));
//        }else if ("139".equals(preNum)) {
//            part4.write(getBytes(key, value));
//        } else {
//            part5.write(getBytes(key, value));
//        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {

        // 关闭资源
        IOUtils.closeStream(part1);
        IOUtils.closeStream(part2);
        IOUtils.closeStream(part3);
        IOUtils.closeStream(part4);
        IOUtils.closeStream(part5);
    }


    public byte[] getBytes(Text key, FlowBean value){
        String s = key + "\t===" + value.toString() + "\r\n";
        return s.getBytes();
    }
}
