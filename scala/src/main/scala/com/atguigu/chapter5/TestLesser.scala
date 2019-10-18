package com.atguigu.chapter5

/**
  * @title: TestLesser
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1811:29
  */
object TestLesser {


  /**
    * （1）return可以省略，Scala会使用函数体的最后一行代码作为返回值
    * （2）返回值类型如果能够推断出来，那么可以省略（:和返回值类型一起省略）
    * （3）如果函数体只有一行代码，可以省略花括号
    * （4）如果函数无参，则可以省略小括号。若定义函数时省略小括号，则调用该函数时，也需省略小括号；若定义函数时未省略，则调用时，可省可不省。
    * （5）如果函数明确声明Unit，那么即使函数体中使用return关键字也不起作用
    * （6）Scala如果想要自动推断无返回值，可以省略等号
    * （7）如果不关心名称，只关系逻辑处理，那么函数名（def）可以省略
    * （8）如果有return，则不能省略返回值类型，必须指定。
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {


    //正常函数声明
    def f(x: String, y: String): String = {
      return x + y
    }

    //1 return 可以省略
    def f1(x: String, y: String): String = {
      x + y
    }

    //2 只有一行，可以省括号
    def f2(x: String, y: String): String = x + y

    //3 scala能推断出返回值类型时，申明的返回值类型可以省略
    def f3(x: String, y: String) = x + y

    //4 对于3的情况，当用了return时，scala就不会做自动推断，所以要显式声明返回值
    def f4(x: String, y: String): String = return x + y

    //5 当返回值是Unit时，可以省略等号“=”
    def f5(x: String, y: String) {
      x + y
    }

    //6 无参函数
    //1、当不省略函数定义的参数括号；调用该函数时，函数名后面的括号可以省略
    //2、当省略函数定义的参数括号；则调用该函数时，函数后面的括号也不能加
    def f6(): Unit = {

    }

    f6
    f6()

    def f7: Unit = {

    }

    f7
    //    f7()

    println("111")
    //*****************************为什么执行到这里后面不执行了？**********************************
      (x: String, y: String) => {
        println("222")
        x + y
        println("333")
      }
        println("444")
      //***************************************************************
    //8 匿名函数定义
    def f8 = (x: String, y: String) => {
      x + y
    }

      //9 匿名函数使用

      def f9(f: (String, String) => String, x: String, y: String): Unit = {
        println(x + y)
        println(f(x, y))
      }

    f9(f8, "123", "abc")

      f9((x: String, y: String) => {
        x + y
      }, "123", "abc")



  }

}
