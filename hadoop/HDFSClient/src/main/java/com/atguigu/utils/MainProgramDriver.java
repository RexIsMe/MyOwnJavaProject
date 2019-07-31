package com.atguigu.utils;

import com.atguigu.entity.ProgramDriver;
import com.atguigu.mr.diywordcount.WcDriver;

import java.lang.reflect.Method;

/**
 * @author Rex
 * @title: MainProgramDriver
 * @projectName HDFSclient
 * @description: 将各个
 * @date 2019/7/288:20
 */
public class MainProgramDriver {

    public static void main(String[] args) throws Exception{
        ProgramDriver programDriver = new ProgramDriver();
        programDriver.run(args);
    }

    public void test() throws Exception{
        WcDriver wcDriver = new WcDriver();
        Method main = wcDriver.getClass().getMethod("main", String[].class);
        String[] str = new String[]{"C:\\Users\\99686\\Desktop\\file\\tes.txt", "C:\\Users\\99686\\Desktop\\file\\wcoutput\\"};
        main.invoke(wcDriver, new Object[]{str});
    }

}
