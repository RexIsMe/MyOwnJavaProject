package com.atguigu.utils;

import org.apache.log4j.Logger;

/**
 * @author Rex
 * @title: Logger
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/2915:06
 */
public class LogUtil {

    public static Logger getLog(Class classType){
        return Logger.getLogger(classType);
    }

}
