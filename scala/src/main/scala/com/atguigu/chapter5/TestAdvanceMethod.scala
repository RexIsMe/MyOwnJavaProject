package com.atguigu.chapter5

/**
  * @title: TestAdvanceMethod
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1814:30
  */
object TestAdvanceMethod {


  /**
    * 1）函数可以作为值进行传递
    * @param args
    */
  def main(args: Array[String]): Unit = {
//    1）函数可以作为值进行传递

    def f1(x:String):String = {
      x + " f1"
    }

    val f11 = f1 _
    println(f11("wer"))
    def f12 = f1 _
    println(f12("wer function"))



    // 2）函数可以作为参数进行传递
    def f2(f:(String) => String, x:String):String = {
      f(x) + " f2"
    }

    println(f2(f1, "test"))


    //3）函数可以作为函数返回值返回
    def f3(s:String) = {

      def f31(x:String):String = {
        println("f31" + x)
        x
      }

      f31 _
    }

    println(f3("test"))
    println(f3("test")("2"))




  }


}
