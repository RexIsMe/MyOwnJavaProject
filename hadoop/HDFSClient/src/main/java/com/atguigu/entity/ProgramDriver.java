package com.atguigu.entity;

import com.atguigu.mr.diywordcount.WcDriver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rex
 * @title: ProgramDriver
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/287:53
 */
public class ProgramDriver {

    private Class classField;
    private String description;

    private static Map<String, ProgramDriver> objMap;
    private Method method;

    static {
        objMap = new HashMap<String, ProgramDriver>();
        objMap.put("wordCount", new ProgramDriver(WcDriver.class, "statistic the times that per word shows"));
    }


    public ProgramDriver() {
    }

    public ProgramDriver(Class cla, String str) {
        this.classField = cla;
        this.description = str;
    }

    private void getMainMethod(ProgramDriver programDriver, String... args){
        try{
            method = programDriver.classField.getMethod("main", args.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据入参执行对应类的对应方法
     * @param args
     */
    public void run(String[] args) throws Exception{
        ProgramDriver programDriver = objMap.get(args[0]);
        if(programDriver == null){
            //TODO 提示信息
        }

        int length = args.length;
        String[] newArgs = null;
        if(length > 1){
            newArgs = new String[length - 1];
            for (int i = 0; i < length - 1; i++) {
                newArgs[i] = args[i + 1];
            }
        }
        getMainMethod(programDriver, newArgs); 
        method.invoke(null, new Object[]{newArgs});
    }

}
