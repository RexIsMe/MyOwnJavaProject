package com.atguigu.chapter8

//import scala.math.sqrt
//import math.sqrt
import scala.math._


/**
  * @title: TestImplicit
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/2510:46
  */
object TestImplicit {

  def main(args: Array[String]): Unit = {


    def t(implicit str:String = "123", name:String): Unit ={
      println(str + " " + name)
    }

    t(name="23123123")


    println(sqrt(2))








  }

}
