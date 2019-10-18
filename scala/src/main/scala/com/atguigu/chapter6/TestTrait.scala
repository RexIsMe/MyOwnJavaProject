package com.atguigu.chapter6

/**
  * @title: TestTrait
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/2111:11
  */
object TestTrait {

  def main(args: Array[String]): Unit = {

    println("123")

    val user1: User11 = new User11()
    println(user1.isInstanceOf[User11])

  }

}

class User11{



}
