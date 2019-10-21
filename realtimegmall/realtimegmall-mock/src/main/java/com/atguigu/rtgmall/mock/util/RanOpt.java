package com.atguigu.rtgmall.mock.util;

/**
 * @author Rex
 * @title: RanOpt
 * @projectName realtimegmall
 * @description: TODO
 * @date 2019/10/1812:20
 */
public class RanOpt<T> {

    T value ;
    int weight;

    public RanOpt ( T value, int weight ){
        this.value=value ;
        this.weight=weight;
    }

    public T getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }
}
