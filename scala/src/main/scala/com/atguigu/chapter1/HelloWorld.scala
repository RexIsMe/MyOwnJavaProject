package com.atguigu.chapter1

import scala.io.StdIn

/**
  * @title: HelloWorld
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1710:31
  */
object HelloWorld {

  def main(args: Array[String]): Unit = {

    println("input age")
    var age = StdIn.readInt()

    val res: String = if (age < 18) {
      "童年"
      "qwe"
    } else if (age >= 18 && age < 30) {
      "中年"
      "qwe"
    } else {
      "老年"
    }

    println(res)


  }

}
