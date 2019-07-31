package com.atguigu.practice._03bothfriend;

import com.atguigu.practice._03bothfriend.job1.MyDriver1;
import com.atguigu.practice._03bothfriend.job1.MyMapper1;
import com.atguigu.practice._03bothfriend.job2.MyDriver2;

/**
 * @author Rex
 * @title: MainDriver
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3111:40
 */
public class MainDriver {

    /**
     * 文件说明：
     * friends.txt
     * 如 A:B,C,D,F,E,O
     * 表示 用户A 关注了 用户B,C,D,F,E,O
     *
     * 需求：
     * 1、根据friends.txt中的数据。查出哪些用户相互关注了，表示：A-B
     * 2、统计出相互关注的两用户都关注了的用户，表示：A-B:E,C
     */

    public static void main(String[] args) throws Exception{

        /**
         * job1先得出被关注的用户都关注了谁，输出friend2.txt
         * 如 E,C 关注了 A ，则表示为 E,C:A
         */
        String[] str1 = new String[]{"C:\\Users\\99686\\Desktop\\file\\practice\\practice\\3\\txt\\friends.txt", "C:\\Users\\99686\\Desktop\\file\\practice\\practice\\3\\output"};
        MyDriver1.doIt(str1);
        /**
         * job2
         * 1、根据friends.txt和friend2.txt两个文件中对应关注和被关注的记录，取交集得到相互关注的用户
         * 2、根据相互关注的两用户，对friends.txt文件中对应两记录取交集即为这两个用户都关注的用户
         */
        String[] str2 = new String[]{"C:\\Users\\99686\\Desktop\\file\\practice\\practice\\3\\txt\\*.txt", "C:\\Users\\99686\\Desktop\\file\\practice\\practice\\3\\output2"};
        MyDriver2.doIt(str2);

    }

}
