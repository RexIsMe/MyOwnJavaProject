package scala0513.com.atguigu.datastructure0513.recursion

object MiGong {
  def main(args: Array[String]): Unit = {

    //构建地图
    val map = Array.ofDim[Int](8, 7)

    //将墙使用1 初始
    //上下
    //内部->外部
    for (i <- 0 until 7) {
      map(0)(i) = 1
      map(7)(i) = 1
    }
    //左右
    for (i <- 0 until 8) {
      map(i)(0) = 1
      map(i)(6) = 1
    }

    map(3)(1) = 1
    map(3)(2) = 1
    //显示地图

    map(2)(2) =1



    for (i <- 0 until 8) {
      for (j <- 0 until 7) {
        print(map(i)(j) + " ")
      }
      println()
    }

    println("\n==开始找路")

    findWay(map, 1,1)

    for (i <- 0 until 8) {
      for (j <- 0 until 7) {
        print(map(i)(j) + " ")
      }
      println()
    }


  }


  //找路
  def findWay(map: Array[Array[Int]], i:Int,j:Int): Boolean = {

    if(map(6)(5) == 2) {
      return true
    } else {

      //根据 点的值
      if(map(i)(j) == 0) {
        //假定该位置时可以走通
        map(i)(j) = 2
        //下->右->上->左->

        //开始探测
        if(findWay(map, i+1,j)) {//下
          return true
        } else if(findWay(map, i,j+1)) { //右
          return true
        } else if(findWay(map, i-1,j)) { //上
          return true
        } else if(findWay(map, i,j-1)) { //左
          return true
        } else {
          map(i)(j) = 3
          return false
        }
      } else { // 1, 2, 3
        return false //
      }
    }
  }
}
