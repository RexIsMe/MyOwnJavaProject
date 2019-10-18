package scala0513.com.atguigu.datastructure0513.sort

import java.text.SimpleDateFormat
import java.util.Date

object InsertSort {
  def main(args: Array[String]): Unit = {
    //val arr = Array(101, 34, 119, 1,-1,999,-90)

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

    insertSort(arr)//插入排序

    val now2: Date = new Date()
    val date2 = dateFormat.format(now2)
    println("排序后时间=" + date2) //输出时间
  }

  def insertSort(arr: Array[Int]): Unit = {

    //第一次的处理


    for(i <- 0 until arr.length-1) {
      var insertIndex = i
      var insertVal = arr(i + 1)

      //
      while (insertIndex >= 0 && insertVal < arr(insertIndex)) {
        //将当前数据后移
        arr(insertIndex + 1) = arr(insertIndex)
        //insertIndex 前移
        insertIndex -= 1
      }

      //退出while时，就找到插入的位置 insertIndex + 1
      arr(insertIndex + 1) = insertVal

    }

    /*
    //第2次的处理 [34,101,119,1]

    insertIndex = 1
    insertVal = arr(1 + 1)

    //
    while (insertIndex >= 0 && insertVal < arr(insertIndex)) {
      //将当前数据后移
      arr(insertIndex + 1) = arr(insertIndex)
      //insertIndex 前移
      insertIndex -= 1
    }

    //退出while时，就找到插入的位置 insertIndex + 1
    arr(insertIndex+1) = insertVal

    println(arr.mkString(" "))

    //第3次的处理 [34,101,119,1]

    insertIndex = 2
    insertVal = arr(2 + 1)

    //
    while (insertIndex >= 0 && insertVal < arr(insertIndex)) {
      //将当前数据后移
      arr(insertIndex + 1) = arr(insertIndex)
      //insertIndex 前移
      insertIndex -= 1
    }

    //退出while时，就找到插入的位置 insertIndex + 1
    arr(insertIndex+1) = insertVal

    println(arr.mkString(" "))*/

  }
}
