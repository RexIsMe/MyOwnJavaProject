package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Rex
 * @title: HdfsIO
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/239:04
 */
public class HdfsIO {

    static Configuration configuration = new Configuration();
    static final String LOCAL_FILE_PATH = "C:\\Users\\99686\\Desktop\\tes.txt";
    static final String DFS_NAME_NODE_URL = "hdfs://hadoop102:9000";
    static final String HADOOP_USERNAME = "atguigu";

    static {
        //因为HADOOP_HOME设置之后没有重启，导致没有生效，用这种方法避免报错
        System.setProperty("hadoop.home.dir", "D:\\software\\worksoftWare\\hadoop\\hadoop-2.7.2");
    }

    /**
     * 用流实现文件 上传
     */
    @Test
    public void putFileToHDFS() throws InterruptedException, URISyntaxException {

        // 1 获取文件系统
//        Configuration configuration = new Configuration();
        /**
         * 如果代码中没有显式设置备份数量，并且resource目录下也没有配置，则取的是默认值3
         * 注意：这里并没有取服务端的配置
         *
         * 由此推断：hdfs每次保存文件时，会在请求中指定该文件的备份个数，直接用hdfs dfs -put 的方式操作时取得服务器中的配置
         * 而使用api时，则由api带过去
         *
         configuration.set("dfs.replication", "2");*/

        FileInputStream fis;
        FSDataOutputStream fos;
        try {
            FileSystem fs = FileSystem.get(new URI(DFS_NAME_NODE_URL), configuration, HADOOP_USERNAME);
            // 2 创建输入流
            fis = new FileInputStream(new File(LOCAL_FILE_PATH));
            // 3 获取输出流
            fos = fs.create(new Path("/190717/gaoxinban/uploadByStream3.txt"));

            try {
                // 4 流对拷
                IOUtils.copyBytes(fis, fos, configuration);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                // 5 关闭资源
                IOUtils.closeStream(fos);
                IOUtils.closeStream(fis);
                fs.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 用流实现文件 下载
     */
    @Test
    public void getFileToHDFS() throws InterruptedException, URISyntaxException {

        // 1 获取文件系统
//        Configuration configuration = new Configuration();

        FileOutputStream fos;
        FSDataInputStream fis;
        try {
            FileSystem fs = FileSystem.get(new URI(DFS_NAME_NODE_URL), configuration, HADOOP_USERNAME);

            // 2 获取输入流
            fis = fs.open(new Path("/user/rex/tar/hadoop-2.7.2.tar.gz"));

            // 3 获取输出流
            fos = new FileOutputStream(new File("C:\\Users\\99686\\Desktop\\file\\hadoop-2.7.2-1.tar.gz"));

            try {
                // 4 流对拷
                IOUtils.copyBytes(fis, fos, configuration);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                // 5 关闭资源
                IOUtils.closeStream(fos);
                IOUtils.closeStream(fis);
                fs.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * 使用流可控下载文件
     *
     * @param distFilePath  远程文件全路径
     * @param localFilePath 本地文件全路径
     * @param begin         文件在远程存储的块起始编号
     * @param end           文件在远程存储的块结束编号
     */
    public static void getFileByOptions(String distFilePath, String localFilePath, Integer begin, Integer end) throws URISyntaxException, IOException, InterruptedException {
        int beginSite = 0;
        int endSite = Integer.MAX_VALUE;
        if (begin != null) {
            beginSite = 1024 * 128 * begin;
        }
        if (end != null) {
            endSite = 1024 * 128 * end;
        }
        FileOutputStream fos;
        FSDataInputStream fis;
        try {
            FileSystem fs = FileSystem.get(new URI(DFS_NAME_NODE_URL), configuration, HADOOP_USERNAME);

            // 2 获取输入流
            fis = fs.open(new Path(distFilePath));

            // 3 获取输出流
            fos = new FileOutputStream(new File(localFilePath));

            try {
                if(beginSite != 0){
                    fis.seek(Long.valueOf(beginSite * 1024));
                }

                byte[] buf = new byte[1024];
                for (int i = 0; i < endSite; i++) {
                    int read = fis.read(buf);
                    while (read == -1){
                        return;
                    }
                    fos.write(buf);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                // 5 关闭资源
                IOUtils.closeStream(fos);
                IOUtils.closeStream(fis);
                fs.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) throws Exception {

        getFileByOptions("/user/rex/tar/hadoop-2.7.2.tar.gz", "C:\\Users\\99686\\Desktop\\file\\hadoop-2.7.2.tar.gz.part1", null, 1);
        getFileByOptions("/user/rex/tar/hadoop-2.7.2.tar.gz", "C:\\Users\\99686\\Desktop\\file\\hadoop-2.7.2.tar.gz.part2", 1, null);

    }

}
