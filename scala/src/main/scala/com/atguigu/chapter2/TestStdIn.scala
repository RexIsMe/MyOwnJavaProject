package com.atguigu.chapter2

import scala.io.StdIn

/**
  * @title: TestStdIn
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1716:24
  */
object TestStdIn {

  def main(args: Array[String]): Unit = {

    println("pls input name")
    var name = StdIn.readLine()
    println("pls input age")
    var age = StdIn.readInt()
    println("pls input gender")
    var gender = StdIn.readLine()


    println("pls check your input ：name = " + name + ", age = " + age + ", gender = " + gender)
  }

}
