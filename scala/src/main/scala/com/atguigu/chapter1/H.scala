package com.atguigu.chapter1

import scala.io.StdIn

/**
  * @title: H
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/2012:25
  */
class H {

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
