package com.atguigu.teach;

public class TestProtected {

    public static void main(String[] args) throws Exception {

        // 访问权限：方法的提供者和方法的调用者之间的关系
        // 方法的提供者: com.atguigu.bigdata.java.A
        // 方法的调用者: com.atguigu.bigdata.java.TestProtected
        // 点表示从属关系 user.name = 用户中的name属性
        A a = new A();
        a.clone();
    }
}
class A {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
