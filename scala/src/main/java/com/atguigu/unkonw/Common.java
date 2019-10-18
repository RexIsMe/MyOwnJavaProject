package com.atguigu.unkonw;

/**
 * @author Rex
 * @title: Common
 * @projectName scala
 * @description: TODO
 * @date 2019/9/2014:10
 */
public class Common {

    public static void main(String[] args) {

        System.out.println(TestClassLoad1.name);
        System.out.println("+==========+");
        System.out.println(TestClassLoad2.name);

        System.out.println(test3());

    }


    public static int test3(){
        int i = 0;
        try{
            return i++;
//            return ++i;
        } finally {
            return ++i;//这两个return共享了i的临时变量，所以返回2
//            ++i;
        }
    }


    /**
     * 查看++的内部执行过程
     */
    public void test1(String str){
        // 拿到常量0，存储到slot 1中
        int a = 0;
        // 将slot 1中的值加载到栈中，对slot 1中的值做加1运算“inc 1,1”,再将加载到栈中的值存到slot 1中
        a = a++;
    }


    public static void test2(){
        short s = Byte.valueOf("5");
        System.out.println(s);
    }

}
