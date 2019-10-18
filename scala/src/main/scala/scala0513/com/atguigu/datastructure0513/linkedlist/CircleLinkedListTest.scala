package scala0513.com.atguigu.datastructure0513.linkedlist

import util.control.Breaks._
object CircleLinkedListTest {
  def main(args: Array[String]): Unit = {
    val josephu = new Josephu
    josephu.addBoy(50)
    josephu.list()
    josephu.countBoy(50, 2, 2)
  }
}

class Josephu {

  var first : Boy = null // 指向第一个小孩


  //实现
  def countBoy(nums:Int, startNo : Int, countNum:Int): Unit = {

    //对参数检测
    if(nums < startNo || startNo <= 0 || countNum<=0) {
      printf("\n 参数有误")
      return
    }

    var temp = first

    //先把 temp 定位到 最后一个小孩
    breakable {
      while (true) {
        if (temp.next == first) {
          break()
        }
        temp = temp.next
      }
    }

    //将temp 定位到 开始数数的小孩的前一个结点 移动 startNo - 1
    for(i <- 0 until startNo-1){
      temp = temp.next
    }

    breakable {
      while (true) {

        if (temp.next == temp) {
          printf("\n 最后出圈是no%d", temp.no)
          break()
        }

        //开始数数 k , 我就让 temp 移动 countNum -1
        for (i <- 0 until countNum - 1) {
          temp = temp.next
        }
        printf("\n no=%d 小孩去圈", temp.next.no)

        //删除
        temp.next = temp.next.next
      }
    }

  }


  //遍历
  /*
  思路
  1) temp = first
  2) 遍历 当 temp.next = frist
   */
  def isEmpty(): Boolean = {
    first == null
  }
  def list(): Unit = {
    if(isEmpty()) {
      println("\n 空链表")
      return
    }

    var temp = first

    breakable {
      while (true) {

        printf("\n no=%d", temp.no)
        //判断是否遍历完毕
        if (temp.next == first) {
          break();
        }
        temp = temp.next
      }
    }
  }

  //添加循环处理
  def addBoy(nums:Int): Unit = {

    var temp: Boy = null
    for(no<- 1 to nums) {
      //先创建小孩
      val boy = new Boy(no)
      if(no == 1) {
        first = boy
        temp = boy
      } else { //循环处理
        temp.next = boy
        boy.next = first
        temp = boy

      }
    }
  }
}

//小孩结点
class Boy(bNO: Int) {
  val no = bNO
  var next: Boy = null
}