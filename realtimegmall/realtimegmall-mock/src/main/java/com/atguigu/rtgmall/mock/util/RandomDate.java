package com.atguigu.rtgmall.mock.util;

import java.util.Date;
import java.util.Random;

/**
 * @author Rex
 * @title: RandomDate
 * @projectName realtimegmall
 * @description: TODO
 * @date 2019/10/1812:20
 */
public class RandomDate {

    Long logDateTime =0L;//
    int maxTimeStep=0 ;


    public RandomDate (Date startDate , Date  endDate, int num) {

        Long avgStepTime = (endDate.getTime()- startDate.getTime())/num;
        this.maxTimeStep=avgStepTime.intValue()*2;
        this.logDateTime=startDate.getTime();

    }


    public  Date  getRandomDate() {
        int  timeStep = new Random().nextInt(maxTimeStep);
        logDateTime = logDateTime+timeStep;
        return new Date( logDateTime);
    }
}
