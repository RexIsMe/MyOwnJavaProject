package com.atguigu.chapter7

import scala.collection.mutable

/**
  * @title: TestNormalOperator
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/2314:45
  */
object TestNormalOperator {

  def main(args: Array[String]): Unit = {

    val intToString: Map[Int, String] = Map(1->"a", 2->"b")


    val ints: List[Int] = List(1,2,3,4,5)


    println(ints.length)
    println(ints.size)

    println(ints.mkString(" "))
    ints.foreach(
      println)

    val iterator: Iterator[Int] = ints.iterator
    while(iterator.hasNext){
      println(iterator.next())
    }

    println(ints.contains(1))
    println(ints.contains(8))

    //########################################################################################################

    println(ints.head)
    println(ints.tail)

    println(ints.last)
    println(ints.init)
    val inits: Iterator[List[Int]] = ints.inits
    while (inits.hasNext) {
      println(inits.next().mkString(","))
    }


    println(ints.reverse)

    println(ints.take(3))
    println(ints.takeRight(3))


    val ints2: List[Int] = List(4,5,6,7,8)

    val ints3: List[Int] = ints.union(ints2)
    println(ints3.mkString(","))

    val ints4: List[Int] = ints.intersect(ints2)
    println(ints4.mkString(","))

    val ints5: List[Int] = ints.diff(ints2)
    println(ints5.mkString(","))


    val tuples: List[(Int, Int)] = ints.zip(ints2)
    println(tuples.mkString(","))

    val iterator2: Iterator[List[Int]] = ints.sliding(2, 2)
    while (iterator2.hasNext) {
      println(iterator2.next())
    }


    println(ints.reduce(_ + _))




    val s1: mutable.Set[Int] = mutable.Set(1,2,3,4)
    val s2: mutable.Set[Int] = s1-=4
    println(s1 == s2)

    val bool: Boolean = s1.add(7)


  }

}
