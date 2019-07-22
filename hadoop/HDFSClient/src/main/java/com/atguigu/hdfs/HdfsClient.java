package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
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

    public static void main(String[] arg0) throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), conf, "atguigu");
        fs.mkdirs(new Path("/0717/gaoxinban"));
        fs.close();
    }


    /**
     * 测试用java调用hadoop创建一个目录
     */
    @Test
    public void testMkdir(){



    }

}
