package com.atguigu.teach;

public class Test {
    public static void main(String[] args) {
        new Child("zhangsan");
    }
}
class Parent {
    public Parent( String name ) {
        System.out.println("name = " + name);
    }
}
class Child extends Parent {
    public Child(String name) {
        super(name);
    }
}
