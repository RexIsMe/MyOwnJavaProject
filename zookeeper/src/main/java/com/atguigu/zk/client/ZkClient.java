package com.atguigu.zk.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rex
 * @title: ZkClient
 * @projectName zkclient
 * @description: TODO
 * @date 2019/7/3015:20
 */
public class ZkClient {


    private String connectString = "hadoop102:2181";
    private int sessionTimeout = 2000;
    ZooKeeper zk;

    /**
     * @Before：在执行@Test之前执行
     * @throws Exception
     */
    @Before
    public void test() throws Exception{
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getState());
                System.out.println("收到回调");
            }
        });

    }


    @Test
    public void getChildren() throws Exception{
        zk.getChildren("/", true);
        Thread.sleep(Long.MAX_VALUE);
    }

}
