package com.atguigu.practice._01multijob;

import com.atguigu.practice._01multijob.job1.MyDriver1;
import com.atguigu.practice._01multijob.job2.MyDriver2;

/**
 * @author Rex
 * @title: MainDriver
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3110:16
 */
public class MainDriver {

    public static void main(String[] args) throws Exception{
        String[] str1 = new String[]{"C:\\Users\\99686\\Desktop\\file\\practice\\practice\\1", "C:\\Users\\99686\\Desktop\\file\\practice\\practice\\1\\output"};
        MyDriver1.doIt(str1);
        String[] str2 = new String[]{"C:\\Users\\99686\\Desktop\\file\\practice\\practice\\1\\result1.txt", "C:\\Users\\99686\\Desktop\\file\\practice\\practice\\1\\2output"};
        MyDriver2.doIt(str2);
    }

}
