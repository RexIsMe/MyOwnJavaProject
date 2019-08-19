package com.example.demo;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Rex
 * @title: IdeaQuickKey
 * @projectName demoNote
 * @description: TODO
 * @date 2019/7/2311:09
 */
public class IdeaQuickKey {

    private static String s;

    public static void main(String[] args) {

        int i = 1;
        float v = 0.3F;


        s = "123";
        System.out.println(s);

        if (s != null) {

        }

        if (s == null) {

        }

        if (s != null) {

        }

        if (s != null) {

        }

        boolean b = true;
        if (!b) {

        }


        ArrayList<Object> objects = new ArrayList<>();

        ArrayList<String> strings = new ArrayList<>();
        for (int i1 = 0; i1 < strings.size(); i1++) {

        }
        for (int i1 = strings.size() - 1; i1 >= 0; i1--) {

        }

        for (String string : strings) {

        }

    }


    @Test
    public void contextLoads() {

        InnerClassTest innerClassTest = new IdeaQuickKey.InnerClassTest();
        innerClassTest.test();
        new IdeaQuickKey().test();


        String str = "adv=cdf";
        System.out.println(str.indexOf("ad"));
    }



    public class InnerClassTest{

        public void test(){
            System.out.println(this);
        }

    }

    public void test(){
        System.out.println(this);
    }
}
