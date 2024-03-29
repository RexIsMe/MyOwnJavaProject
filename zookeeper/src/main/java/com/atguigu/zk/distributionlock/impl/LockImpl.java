package com.atguigu.zk.distributionlock.impl;

import com.atguigu.zk.distributionlock.Lock;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Rex
 * @title: LockImpl
 * @projectName zkclient
 * @description: TODO
 * @date 2019/8/1611:45
 */
public class LockImpl implements Lock {

    private static final Logger log = Logger.getLogger(LockImpl.class.getName());

    public boolean lock() throws Exception {
        try {
            boolean locked = false;

            locked = tryLock();

            if (locked) {
                return true;
            }
            while (!locked) {

                await();


                if (checkLocked()) {
                    locked=true;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            unlock();
        }

        return false;
    }

    public boolean unlock() {
        return false;
    }



    private boolean tryLock() throws Exception {
        //创建临时Znode
        List<String> waiters = getWaiters();
        locked_path = ZKclient.instance
                .createEphemeralSeqNode(LOCK_PREFIX);
        if (null == locked_path) {
            throw new Exception("zk error");
        }
        locked_short_path = getShorPath(locked_path);

        //获取等待的子节点列表，判断自己是否第一个
        if (checkLocked()) {
            return true;
        }

        // 判断自己排第几个
        int index = Collections.binarySearch(waiters, locked_short_path);
        if (index < 0) { // 网络抖动，获取到的子节点列表里可能已经没有自己了
            throw new Exception("节点没有找到: " + locked_short_path);
        }

        //如果自己没有获得锁，则要监听前一个节点
        prior_path = ZK_PATH + "/" + waiters.get(index - 1);

        return false;
    }



    private boolean checkLocked() {
        //获取等待的子节点列表

        List<String> waiters = getWaiters();
        //节点按照编号，升序排列
        Collections.sort(waiters);

        // 如果是第一个，代表自己已经获得了锁
        if (locked_short_path.equals(waiters.get(0))) {
            log.info("成功的获取分布式锁,节点为{}", locked_short_path);
            return true;
        }
        return false;
    }


    private void await() throws Exception {

        if (null == prior_path) {
            throw new Exception("prior_path error");
        }

        final CountDownLatch latch = new CountDownLatch(1);


        //订阅比自己次小顺序节点的删除事件
        Watcher w = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
                log.info("[WatchedEvent]节点删除");

                latch.countDown();
            }
        };

        client.getData().usingWatcher(w).forPath(prior_path);

        latch.await(WAIT_TIME, TimeUnit.SECONDS);
    }
}
