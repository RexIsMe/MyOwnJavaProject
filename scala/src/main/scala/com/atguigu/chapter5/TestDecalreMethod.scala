package com.atguigu.chapter5

/**
  * @title: TestMethod
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1810:32
  */
object TestDecalreMethod {

  /**
    * （1）函数1：无参，无返回值
    * （2）函数2：无参，有返回值
    * （3）函数3：有参，无返回值
    * （4）函数4：有参，有返回值
    * （5）函数5：多参，无返回值
    */
  def main(args: Array[String]): Unit = {
    //1
    def f1():Unit = {
      println("f1")
    }

    //2
    def f2():Int = {
      1
    }

    //3
    def f3(x:Int):Unit = {
      println("f3" + " " +x)
    }

    //4
    def f4(x:Int):Int = {
      x * 2
    }

    //5
    def f5(x:Int, y:Int):Int = {
      x + y
    }



  }



}
