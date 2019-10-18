package com.atguigu;

import java.io.InputStream;

/**
 * @author Rex
 * @title: Common
 * @projectName scala
 * @description: TODO
 * @date 2019/9/2711:17
 */
public class Common {

    public static void main(String[] args) {
        System.out.println("11111");


        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.xml");
        System.out.println(is.toString());
    }

}
