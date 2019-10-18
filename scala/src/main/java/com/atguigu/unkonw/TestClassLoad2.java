package com.atguigu.unkonw;

import scala.Int;

/**
 * @author Rex
 * @title: TestClassLoad1
 * @projectName scala
 * @description: TODO
 * @date 2019/9/2015:13
 */
public class TestClassLoad2 {

    public final static Integer name = 666;

    static{
        System.out.println("TestClassLoad2 classload......");
    }

}
