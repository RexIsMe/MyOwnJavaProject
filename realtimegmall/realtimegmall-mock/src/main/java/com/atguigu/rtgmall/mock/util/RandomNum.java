package com.atguigu.rtgmall.mock.util;

import java.util.Random;

/**
 * @author Rex
 * @title: RandomNum
 * @projectName realtimegmall
 * @description: TODO
 * @date 2019/10/1812:21
 */
public class RandomNum {

    public static final  int getRandInt(int fromNum,int toNum){
        return   fromNum+ new Random().nextInt(toNum-fromNum+1);
    }

}
