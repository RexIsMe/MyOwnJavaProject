package com.atguigu.chapter7

/**
  * @title: TestBasicMethod
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/248:39
  */
object TestBasicMethod {

  def main(args: Array[String]): Unit = {

    val ints: List[Int] = List(1,2,3,4)

    println(ints.sum)

    println(ints.product)

    println(ints.max)

    println(ints.min)

    val ints2: List[Int] = ints.updated(2,5)
    println(ints2.sorted)
    val ints3: List[Int] = ints.sortBy(x => {
      if (x == 2) {
        x * 100
      }
      x
    })
    println(ints3)


    val ints5: List[Int] = ints.sortWith((x:Int, y:Int) => {x > y})
    println(ints5)

  }

}
