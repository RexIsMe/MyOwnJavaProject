package com.atguigu.chapter7

/**
  * @title: Practice
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/2316:19
  */
object Practice {

  def main(args: Array[String]): Unit = {
    val ints: List[Int] = List(1,2,3,4)
    println(listSum(ints))
  }

  /**
    * 计算一个list[Int]的和
    */
  def listSum(list:List[Int]):Int = {
    if(list.size < 1){
      return 0
    }

    if (list.size == 1) {
      return list(0)
    }

    val tmp:Int = list(0) + list(1)
    val ints: List[Int] = tmp+:list.tail.tail
    listSum(ints)
  }

}
