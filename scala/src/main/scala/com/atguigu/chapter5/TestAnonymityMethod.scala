package com.atguigu.chapter5

/**
  * @title: TestAnonymityMethod
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1816:02
  */
object TestAnonymityMethod {

  /**
    * （1）参数的类型可以省略，会根据形参进行自动的推导
    * （2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过1的永远不能省略圆括号。
    * （3）匿名函数如果只有一行，则大括号也可以省略
    */
  def main(args: Array[String]): Unit = {
    //1
    def f(x: Int, y: Int, f: (Int, Int) => Int): Int = {
      f(x, y)
    }

    f(1, 2, (x: Int, y: Int) => {
      x + y
    })


  }

}
