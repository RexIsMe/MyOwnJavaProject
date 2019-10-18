package com.atguigu.test

import scala.collection.mutable.ArrayBuffer

/**
  * @title: Common
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/2010:10
  */
object Common {

  def main(args: Array[String]): Unit = {
    val tuples: List[(String, Int)] = List(("a", 1), ("b", 2))

    println(tuples.map(t => t._1))

    implicit def tr(d:Double):Int = {
      d.intValue()
    }

    var i : Int = 5.0
    println(i)




    val b = ArrayBuffer[Int]()
    b += (1,2,3,4,5)
    println(b)

    b.trimEnd(3)
    println(b)


    val temp = List(1,2,3)
//    println((0 /: temp) (_ + _))

    val tt = List(4,5,6)

//    println(temp.fold(2)((x,y) => x+y))
//    println(temp.foldLeft(tt)((list,i) =>  list.map(x => x + i)))

    println(temp.scanLeft(0)(_ + _))

  }


  @throws(classOf[NumberFormatException])
  @throws(classOf[NullPointerException])
  def f11()={
    "abc".toInt
  }

}
