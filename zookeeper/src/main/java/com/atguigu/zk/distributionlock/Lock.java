package com.atguigu.zk.distributionlock;

/**
 * @author Rex
 * @title: Lock
 * @projectName zkclient
 * @description: TODO
 * @date 2019/8/1611:44
 */
public interface Lock {

    boolean lock() throws Exception;

    boolean unlock();
}
