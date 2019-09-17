package com.atguigu.chapter2

/**
  * @title: VariableAndFinalVar
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1716:08
  */
object VariableAndFinalVar {

  /**
    * （1）声明变量时，类型可以省略，编译器自动推导，即类型推导
    * （2）类型确定后，就不能修改，说明Scala是强数据类型语言。
    * （3）变量声明时，必须要有初始值
    * （4）在声明/定义一个变量时，可以使用var或者val来修饰，var修饰的变量可改变，
    * （5）var修饰的对象引用可以改变，val修饰的对象则不可改变，但对象的状态（值）却是可以改变的。（比如：自定义对象、数组、集合等等）
    * @param args
    */
  def main(args: Array[String]): Unit = {

    var in:Int = 1

    /*(1)*/
    var n1 = 2

    /*(2)*/
    var n2 = 3
    //n1 = "";

    /*(3)*/
//    var n3

    /*(4)*/
    var n41 = 1
    n41 = 11

    val n42 = 2
//    n42 = 22

    /*(5)*/
    var n51 = new Dog
    n51 = null
    n51.name = "san san"

    val n52 = new Dog
//    n52 = null
    n52.name = "sdf"
  }


}


class Dog{

  var name = "GOU DAN"

}