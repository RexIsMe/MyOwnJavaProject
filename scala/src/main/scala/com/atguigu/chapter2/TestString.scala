package com.atguigu.chapter2

/**
  * @title: TestString
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1716:17
  */
object TestString {

  /**
    * （1）字符串，通过+号连接
    * （2）printf用法：字符串，通过%传值。
    * （3）字符串模板：通过$获取变量值
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    println("1" + "2")

    var name = "rex"
    var age = 25
    printf("p1 = %s, p2 = %d", name, age)


    var module =
      s"""
        |The magic word are
        |To know , is to konw nothing, that is meaning of true knowledge
        |$name $age
      """.stripMargin
    println(module)

  }

}
