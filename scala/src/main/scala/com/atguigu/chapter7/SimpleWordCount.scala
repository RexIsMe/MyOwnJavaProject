package com.atguigu.chapter7

/**
  * @title: SimpleWordCount
  * @projectName scala
  * @description: TODO
  * @author Rex
  * @date 2019/9/249:09
  */
object SimpleWordCount {

  def main(args: Array[String]): Unit = {
    val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

    val stringses: List[Array[String]] = stringList.map(x => x.split(" "))

    val flatten: List[String] = stringses.flatten
    println(flatten)

    val stringToStrings: Map[String, List[String]] = flatten.groupBy(x => x)
    println(stringToStrings)
    val stringToInt: Map[String, Int] = stringToStrings.map(x => (x._1, x._2.size))
    println(stringToInt)

    val list: List[(String, Int)] = stringToInt.toList
    println(list.sortWith((x, y) => {
      x._2 > y._2
    }).take(3))


    val _: Int = 1

  }


}
