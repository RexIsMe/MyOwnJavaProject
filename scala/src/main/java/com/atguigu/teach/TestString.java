package com.atguigu.teach;

import java.lang.reflect.Field;

public class TestString {
    public static void main(String[] args) throws Exception {
        //String s = "a" + "b";
        //int b = 10 + 10;
        String s = " a b ";
        //s = s.trim();
        //System.out.println("!"+s+"!");  // !a b!

        // 反射
        Class aClass = s.getClass();
        Field value = aClass.getDeclaredField("value");
        value.setAccessible(true);
        char[] cs = (char[])value.get(s);
        cs[2] = 'd';

        System.out.println(s);
    }
}
