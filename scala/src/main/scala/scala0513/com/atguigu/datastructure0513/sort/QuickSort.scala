package scala0513.com.atguigu.datastructure0513.sort

import java.text.SimpleDateFormat
import java.util.Date

import util.control.Breaks._

object QuickSort {
  def main(args: Array[String]): Unit = {

    //val arr = Array(-9, 78, 0, 23, -567, 70)

    val random = new util.Random()
    val arr = new Array[Int](8000000) //80000
    for (i <- 0 until 8000000) {
      arr(i) = random.nextInt(8000000)
    }
    println("排序前")
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = dateFormat.format(now)
    println("排序前时间=" + date) //输出时间

    quickSort(0, arr.length -1, arr)

    val now2: Date = new Date()
    val date2 = dateFormat.format(now2)
    println("排序后时间=" + date2) //输出时间

   // println(arr.mkString(" "))//(-9, -567, 0, 23, 78, 70)



  }


  //阅读其他程序员的代码是一个能力
  //0->看文档-> 看代码 [1周] 人->

  /**
    *
    * @param left  数组的左边下标
    * @param right 数组的右边下标
    * @param arr   准备排序的数组
    */
  def quickSort(left: Int, right: Int, arr: Array[Int]): Unit = {
    var l = left //赋值
    var r = right //赋值

    //pivot 是基数， 该数据中间的元素
    var pivot = arr((left + right) / 2)

    var temp = 0
    breakable {
      while (l < r) {
        //在pivot 左边找到一个 大于或者等于 pivot
        while (arr(l) < pivot) {
          l += 1
        }
        //在pivot 右边找到一个 小于或者等于 pivot
        while (arr(r) > pivot) {
          r -= 1
        }
        if (l >= r) {//保证将所有比pivot 小的放到左边，比pivot 大
          break()
        }

        //交换
        var temp = arr(l)
        arr(l) = arr(r)
        arr(r) = temp

        //防止交换的数以相同，造成死循环
        if (arr(l) == pivot) {
          r -= 1
        }
        if ((arr(r)) == pivot) {
          l += 1
        }
      }
    }
    if (l == r) {//处理，就是当 l
      l += 1
      r -= 1
    }
    //向左递归
        if (left < r) {
          quickSort(left, r, arr)
        }
    //向右递归
        if (right > l) {
          quickSort(l, right, arr)
        }
  }

}
