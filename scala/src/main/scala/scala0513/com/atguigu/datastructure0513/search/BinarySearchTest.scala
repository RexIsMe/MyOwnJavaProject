package scala0513.com.atguigu.datastructure0513.search

object BinarySearchTest {
  def main(args: Array[String]): Unit = {

    val arr = Array(4, 5, 90, 100, 900, 1000)

    val index = binarySearch2(arr, 0, arr.length - 1, 6)
    println("index=" + index)




  }

  //递归二分
  //如果找到就返回对应index,如果没有返回-1
  def binarySearch(arr: Array[Int], l: Int, r: Int, findVal: Int): Int = {

    if (l > r) { //找不到
      return -1
    }

    //先找到中间数
    val midIndex = (l + r) / 2
    if (arr(midIndex) > findVal) { //向左递归
      return binarySearch(arr, l, midIndex - 1, findVal)
    } else if (arr(midIndex) < findVal) {
      //向右递归
      return binarySearch(arr, midIndex + 1, r, findVal)
    } else {
      return midIndex
    }

  }

  //非递归二分
  def binarySearch2(arr: Array[Int], l: Int, r: Int, findVal: Int): Int = {

    var left = l
    var right = r
    var midIndex = 0

    while (left <= right) {
      midIndex = (left + right) / 2
      if (arr(midIndex) > findVal) { //向左
        right = midIndex -1
      }else if(arr(midIndex) < findVal) {//向右
        left = midIndex+1
      } else {

        return midIndex
      }
    }

    return -1
  }
}
