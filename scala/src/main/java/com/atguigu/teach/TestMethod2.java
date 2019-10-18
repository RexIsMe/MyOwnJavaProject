package com.atguigu.teach;

/**
 * @author Rex
 * @title: TestMethod2
 * @projectName scala
 * @description: TODO
 * @date 2019/9/218:40
 */
public class TestMethod2 {

    public static void main(String[] args) {

        Byte b = 1;
        System.out.println(b.getClass().getName());
        test(b);

    }

    public static void test(char c){
        System.out.println("ccccc");
    }
//    public static void test(byte b){
//        System.out.println("bbbbb");
//    }
//    public static void test(short s){
//        System.out.println("sssss");
//    }
    public static void test(int i){
        System.out.println("iiiii");
    }

}
