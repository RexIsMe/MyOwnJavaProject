package com.atguigu.chapter2

/**
  * @title: TestFor
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1715:39
  */
object TestFor {

  def main(args: Array[String]): Unit = {

    for(i <- 1 to 5) println(i)

    for(i <- 1 to 5 reverse) println(i)

    for(i <- 5 to 1 by -1) println(i)

  }

}
