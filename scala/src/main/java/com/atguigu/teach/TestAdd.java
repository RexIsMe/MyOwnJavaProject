package com.atguigu.teach;

public class TestAdd {
    public static void main(String[] args) {

        int i = 0;
        //int j = i++;
        i = i++; // 1) _tmp = i++, _tmp = 0, i=1 2) i = _tmp = 0

        //System.out.println("i = " + i + ",j = " + j);
        System.out.println("i = " + i);
    }
}
