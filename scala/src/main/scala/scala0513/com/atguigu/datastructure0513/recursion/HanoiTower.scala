package scala0513.com.atguigu.datastructure0513.recursion

object HanoiTower {
  def main(args: Array[String]): Unit = {

    //测试
    hanoiTower(500,'A','B','C')

  }


  def hanoiTower(num:Int, a: Char, b: Char,c:Char): Unit = {

    //如果只有一个盘
    if(num == 1) {
      printf("\n 移动盘 %c->%c", a, c)
    } else { //如果是多个盘
//      将 上面的所有盘 A->B ， 通过C
//      将 最下面的盘 A->C
//      将B它的所有盘 B->C, 通过 A
      hanoiTower(num-1,a,c, b )
      printf("\n 移动盘 %c->%c", a,c)
      hanoiTower(num-1,b,a, c)
    }
  }

}
