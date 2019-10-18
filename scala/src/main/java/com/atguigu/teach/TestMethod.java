package com.atguigu.teach;

public class TestMethod {
    public static void main(String[] args) {

    }
}
class AA {
    public int i = 10;
    public int getSum() {
        return i + 10;
    }
}
class BB extends AA {
    public int i = 20;
    public int getSum() {
        return i + 20;
    }
}
