package com.atguigu.chapter4

import scala.util.control.Breaks

/**
  * @title: ProcessControl
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/1716:42
  */
object ProcessControl {

  def main(args: Array[String]): Unit = {

      testBreak()
    testContinue
  }

  /**
    * If
    */
  def testIf():Unit = {
    var ret = if (true) "yes" else 1
    println(ret + " " + ret.getClass)

    var ret2 = if(true){
      "1"
    } else if (false) {
      "2"
    } else {
      "3"
    }
    println(ret2)


    if(true){
      if(true){
        println(1)
      }
    }
  }

  /**
    * For
    */
  def testFor():Unit = {
    for (elem <- 1 to 5) {
      println(elem)
    }
    for (elem <- 1 to 5 reverse) {
      println(elem)
    }
    for (elem <- 5 to 1 by -1) {
      println(elem)
    }
  }


  /**
    * Break
    */
  def testBreak():Unit = {

    Breaks.breakable{
      for(i <- 1 to 5){
        if(i == 2){
          Breaks.break()
        }
        println(i)
      }
    }

  }

  /**
    * Continue
    */
  def testContinue():Unit = {

      for(i <- 1 to 5){
        if(i == 2){
        }else
        println(i)
      }

  }


}
