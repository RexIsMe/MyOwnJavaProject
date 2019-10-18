package scala0513.com.atguigu.datastructure0513.sort

import java.text.SimpleDateFormat
import java.util.Date

object SelectSort {
  def main(args: Array[String]): Unit = {

    //val arr = Array(101, 34, 119, 1,-1, 990, -99,678)

    val random = new util.Random()
    val arr = new Array[Int](80000) //80000
    for (i <- 0 until 80000) {
      arr(i) = random.nextInt(8000000)
    }
    println("排序前")
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = dateFormat.format(now)
    println("排序前时间=" + date) //输出时间

    selectSort(arr)

    val now2: Date = new Date()
    val date2 = dateFormat.format(now2)
    println("排序后时间=" + date2) //输出时间
  }

  //选择排序
  def selectSort(arr:Array[Int]): Unit = {

    //第一次
    /*

    选择排序思路
    1)  假定 arr(0) 最小值
    minVal = arr(0)
    minIndex = 0
    2) 和后面所有的元素比较，如果有更小的就交换
    3) 遍历后 确定本次 循环的最小值
    4) 交换
    arr(minIndex) = arr(0)
    arr(0) = minVal
    [101, 34, 119, 1]
    [1,[ 34, 119, 101]

     */

    for(i <- 0 until arr.length-1) {
      var minVal = arr(i)
      var minIndex = i

      for (j <- i + 1 until arr.length) {
        if (minVal > arr(j)) {
          //做变化
          minVal = arr(j)
          minIndex = j
        }
      }

      //交换
      if (minIndex != i) {
        arr(minIndex) = arr(i)
        arr(i) = minVal
      }

     // println(arr.mkString(" "))
    }

    /*

    //第一次
    /*

    选择排序思路
    1)  假定 arr(0) 最小值
    minVal = arr(0)
    minIndex = 0
    2) 和后面所有的元素比较，如果有更小的就交换
    3) 遍历后 确定本次 循环的最小值
    4) 交换
    arr(minIndex) = arr(0)
    arr(0) = minVal
    [101, 34, 119, 1]
    [1,[ 34, 119, 101]

     */

    minVal = arr(1)
    minIndex = 1

    for(j <- 1+1 until arr.length) {
      if(minVal > arr(j)) {
        //做变化
        minVal = arr(j)
        minIndex = j
      }
    }

    //交换
    if(minIndex != 1) {
      arr(minIndex) = arr(1)
      arr(1) = minVal
    }

    println(arr.mkString(" "))


    minVal = arr(2)
    minIndex = 2

    for(j <- 2+1 until arr.length) {
      if(minVal > arr(j)) {
        //做变化
        minVal = arr(j)
        minIndex = j
      }
    }

    //交换
    if(minIndex != 2) {
      arr(minIndex) = arr(2)
      arr(2) = minVal
    }

    println(arr.mkString(" "))*/
  }
}
