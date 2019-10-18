package com.atguigu.chapter5

import scala.io.StdIn

/**
  * @title: TestMethodAndWay
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1810:38
  */
object TestMethodAndWay {

  //2 直接外層就是
  def main():Int={
    1
  }

  /**
    * （1）Scala语言的语法非常灵活，可以在任何的语法结构中声明任何的语法
    * （2）函数没有重载和重写的概念；方法可以进行重载和重写
    * （3）Scala中函数可以嵌套定义
    */
  def main(args: Array[String]): Unit = {

    //1
    println("pls input something")
    import scala.io.StdIn
    var a = StdIn.readLine()

    //2
//    def f1(): Unit = {
//
//    }
//
//    def f1(x:Int):Unit={
//
//    }

    //3
    def f2: Unit ={

      def f3: Unit ={

      }

    }
    
  }


}
