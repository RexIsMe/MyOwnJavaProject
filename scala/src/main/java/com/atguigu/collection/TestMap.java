package com.atguigu.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rex
 * @title: TestMap
 * @projectName scala
 * @description: TODO
 * @date 2019/9/2310:29
 */
public class TestMap {

    public static void main(String[] args) {
        Map map = new HashMap<User, Integer>();
        for (int i = 0; i < 12; i++) {
            User u = new User();
            map.put(u, i);
            System.out.println(i + ": ");
        }
    }

}

class User{

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
