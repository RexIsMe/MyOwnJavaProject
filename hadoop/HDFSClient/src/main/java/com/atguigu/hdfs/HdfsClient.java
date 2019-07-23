package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Rex
 * @title: HdfsClient
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2214:58
 */
public class HdfsClient {

    static Configuration configuration = new Configuration();
    static String localFilePath = "C:\\Users\\99686\\Desktop\\tes.txt";

    {
        //因为HADOOP_HOME设置之后没有重启，导致没有生效，用这种方法避免报错
        System.setProperty("hadoop.home.dir", "D:\\software\\worksoftWare\\hadoop\\hadoop-2.7.2");
    }

    public static void main(String[] arg0) throws URISyntaxException, IOException, InterruptedException {
//        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "atguigu");
        fs.mkdirs(new Path("/190717/gaoxinban"));
        fs.close();
    }


    @Test
    public void testCopyFromLocalFile() throws IOException, InterruptedException, URISyntaxException {

        // 1 获取文件系统
//        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "2");
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "atguigu");

        // 2 上传文件
        fs.copyFromLocalFile(new Path("C:\\Users\\99686\\Desktop\\file\\hadoop-2.7.2.tar.gz"), new Path("/user/rex/tar/hadoop-2.7.2.tar.gz"));

        // 3 关闭资源
        fs.close();

        System.out.println("over");
    }




}
