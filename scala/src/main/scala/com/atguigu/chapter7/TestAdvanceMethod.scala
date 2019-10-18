package com.atguigu.chapter7


/**
  * @title: TestAdvanceMethod
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/248:48
  */
object TestAdvanceMethod {

  def main(args: Array[String]): Unit = {

    val ints: List[Int] = List(1,2,3,4)

    val ints1: List[Int] = ints.filter(x =>x%2 == 0)
    println(ints1)

    val ints2: List[Int] = ints.map(x => x*2)
    println(ints2)
    println(ints.map(x => (x, x * 2)))
    val tuples: List[(Int, Int)] = ints.map(x => (x, x * 2))
    println(tuples)


    val list: List[List[Int]] = List(ints,List(5,6))
    println(list.flatten)

    println(list.flatten.map(x => x*2))
    println(list.flatMap(x => x.map(y => y * 2)))

    println(ints.groupBy(x => x % 2))



    println(ints.reduce((x, y) => x + y))
    println(ints.reduce((x, y) => x * y))

    //zedie
    println(ints.fold(5)((x, y) => x + y))

    val stringToInt1: Map[String, Int] = Map("a" -> 1, "b" -> 2, "c" -> 3)
    val stringToInt2: Map[String, Int] = Map("a" -> 1, "b" -> 2, "d" -> 3)





  }

}
