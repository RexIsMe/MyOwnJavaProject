package scala0513.com.atguigu.datastructure0513.sort

import java.text.SimpleDateFormat
import java.util.Date
object BubbleSort {
  def main(args: Array[String]): Unit = {
    //数组
    //val arr = Array(3, 9, -1, 10, 20)
    //创建一个80000个随机数据的数组
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



    bubbleSort(arr) //冒泡

    val now2: Date = new Date()
    val date2 = dateFormat.format(now2)
    println("排序后时间=" + date2) //输出时间
  }
  def bubbleSort(arr: Array[Int]): Unit = {

    for (i <- 0 until arr.length - 1) {
      for (j <- 0 until arr.length - 1 - i) {
        if (arr(j) > arr(j + 1)) {
          val temp = arr(j)
          arr(j) = arr(j + 1)
          arr(j + 1) = temp
        }
      }
    }
  }
}
