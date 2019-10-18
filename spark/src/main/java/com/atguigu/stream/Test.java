package com.atguigu.stream;

import javafx.scene.shape.Path;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * @author Rex
 * @title: Test
 * @projectName spark
 * @description: TODO
 * @date 2019/9/2716:28
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException {


        FileInputStream fileInputStream = new FileInputStream("");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);


    }


}
