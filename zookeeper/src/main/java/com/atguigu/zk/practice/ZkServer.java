package com.atguigu.zk.practice;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author Rex
 * @title: ZkServer
 * @projectName zkclient
 * @description: TODO
 * @date 2019/7/3015:53
 */
public class ZkServer {

//    private static String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private static String connectString = "hadoop102:2181";
    private static int sessionTimeout = 2000;
    private ZooKeeper zk = null;
    private String parentNode = "/servers";

    // 创建到zk的客户端连接
    public void getConnect() throws IOException {

        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

            public void process(WatchedEvent event) {

            }
        });
    }

    // 注册服务器
    public void registServer(String hostname) throws Exception{

        String create = zk.create(parentNode + "/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(hostname +" is online "+ create);
    }

    // 业务功能
    public void business(String hostname) throws Exception{
        System.out.println(hostname+" is working ...");

        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {

        // 1获取zk连接
        ZkServer server = new ZkServer();
        server.getConnect();

        // 2 利用zk连接注册服务器信息
        server.registServer(args[0]);

        // 3 启动业务功能
        server.business(args[0]);
    }




}
