package scala0513.com.atguigu.datastructure0513.sort

import java.text.SimpleDateFormat
import java.util.Date

object MergeSort {
  def main(args: Array[String]): Unit = {
    //val arr = Array(9,8,7,6,5,4,3,2,1)

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
    val temp = new Array[Int](arr.length)

    mergeSort(arr, 0, arr.length-1, temp)//归并排序

    val now2: Date = new Date()
    val date2 = dateFormat.format(now2)
    println("排序后时间=" + date2) //输出时间

   // println(arr.mkString(" "))
  }

  /**
    * 分过程
    * @param arr
    * @param left
    * @param right
    * @param temp
    */
  def mergeSort(arr: Array[Int],left:Int,right :Int,temp:Array[Int]): Unit = {
    if(left<right){
      val mid = (left+right)/2
      mergeSort(arr,left,mid,temp) //左递归
      mergeSort(arr,mid+1,right,temp) //右递归
      merge(arr,left,mid,right,temp)//合并

    }
  }



  /**
    * 合并
    * @param arr 数组
    * @param left 是合并时，第一个有序序列的左边的下标
    * @param mid 第一个有序序列的最右边的下标 mid+1 就是第2个有序序列的左边的下标
    * @param right 就是第2个有序序列的最右边的下标
    * @param temp 临时数组，做中转
    */
  def merge(arr: Array[Int], left: Int, mid: Int, right: Int, temp: Array[Int]) {
    var i = left //i
    var j = mid + 1 // 就是第2个有序序列的左边的下标
    var t = 0 // 临时数组的下标


    //第一个阶段
    //说明
    //1.
    while (i <= mid && j <= right) {//表示两个有序序列，还没有完全遍历完毕
      //如果第一个有序序列的当前元素小于等于 第2个有序序列的当前元素
      //将 第一个有序序列的当前元素 拷贝 temp数组
      //t += 1 i + = 1
      if (arr(i) <= arr(j)) {
        temp(t) = arr(i)
        t += 1
        i += 1
      } else { //反之将 第2个有序序列的当前元素 拷贝 temp数组 t += 1 j+=1
        temp(t) = arr(j)
        t += 1
        j += 1
      }
    }


    //第二个阶段的工作
    //如果第一个有序序列有剩余数据，就依次拷贝temp
    while (i <= mid) {
      temp(t) = arr(i)
      t += 1
      i += 1
    }
    //如果第2个有序序列有剩余数据，就依次拷贝temp
    while (j <= right) {
      temp(t) = arr(j)
      t += 1
      j += 1
    }


    //将 temp 数组的数据拷贝给原始数组
    // tempLeft 不是  temp.lenght -1
    t = 0
    var tempLeft = left
    while (tempLeft <= right) {
      arr(tempLeft) = temp(t)
      t += 1
      tempLeft += 1
    }
  }

}
