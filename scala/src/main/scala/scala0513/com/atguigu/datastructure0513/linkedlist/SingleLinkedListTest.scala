package scala0513.com.atguigu.datastructure0513.linkedlist

import util.control.Breaks._
object SingleLinkedListTest {
  def main(args: Array[String]): Unit = {
    //测试
    val singleLinkedList = new SingleLinkedList()
//    singleLinkedList.addHeroNode(new HeroNode(1, "宋江", "及时雨"))
//    singleLinkedList.addHeroNode(new HeroNode(3, "吴用", "智多星"))
//    singleLinkedList.addHeroNode(new HeroNode(2, "卢俊义", "玉麒麟"))
    singleLinkedList.addByOrderNO(new HeroNode(1, "宋江", "及时雨"))
    singleLinkedList.addByOrderNO(new HeroNode(3, "吴用", "智多星"))
    singleLinkedList.addByOrderNO(new HeroNode(2, "卢俊义", "玉麒麟"))
    singleLinkedList.addByOrderNO(new HeroNode(21, "卢俊义21", "玉麒麟"))
    singleLinkedList.addByOrderNO(new HeroNode(11, "吴用11", "玉麒麟"))


    //遍历
    singleLinkedList.list()

//    //修改
//    singleLinkedList.update(new HeroNode(1, "小明", "不下雨"))
//
//    println("\n 修改后")
//
//    singleLinkedList.list()
//
//    //删除
//    singleLinkedList.delHeroNode(1)
//    singleLinkedList.delHeroNode(3)
//    singleLinkedList.delHeroNode(2)
//
//    println("\n 删除后")
//    singleLinkedList.list()
  }
}

//创建单链表
class SingleLinkedList {

  //标志链表的头
  val head = new HeroNode(-1, "","")



  //添加按照no 从小到大插入
  def addByOrderNO(heroNode: HeroNode): Unit = {

    var temp = head
    var flag = false

    breakable {
      //循环
      while (true) {
        //判断是否已经是最后
        if (temp.next == null) {
          flag = true
          break()
        }
        if (temp.next.no > heroNode.no) {
          //找到
          flag = true
          break()
        } else if (temp.next.no == heroNode.no) {
          //已经存在
          break()

        }
        //后移
        temp = temp.next
      }
    }
    if(flag) {
      //添加
      heroNode.next = temp.next
      temp.next = heroNode

    } else {
      printf("\n no=%d 结点已经存在", heroNode.no)
    }
  }

  //删除
  def delHeroNode(delNO:Int): Unit = {

    if(isEmpty()) {
      println("链表空")
      return
    }

    var temp = head
    var flag = false //标志
    //定位 temp到要删除结点的前一个结点
    breakable {
      while (true) {

        if (temp.next.no == delNO) {
          //找到
          flag = true
          break()
        }

        if (temp.next.next == null) {
          // 找不到
          break()
        }

        //后移
        temp = temp.next
      }
    }
    if(flag) {
      //删除
      temp.next = temp.next.next
    } else {
      println("\n 要删除no=%d 不存在", delNO)
    }
  }
  //修改

  /*

   */

  def update(heroNode: HeroNode): Unit = {
    if(isEmpty()) {
      println("链表空")
      return
    }

    //遍历查找=>AVL 红黑树
    var temp = head.next
    var  flag = false // 标识是否找到要修改的结点
    breakable {
      //找-> 其它
      while (true) {
        //比较
        if (temp.no == heroNode.no) {
          //找到
          flag = true
          break() //找到，可以
        }

        //判断是否最后节点
        if (temp.next == null) {
          break() //没有找到
        }

        //temp 后移，看下一个节点
        temp = temp.next
      }
    }

    if(flag) {
      //内容替换
      temp.name = heroNode.name
      temp.nickname = heroNode.nickname

      //替换结点[需要让temp 定位到要修改的结点的前一个结点, 讲删除]
    } else {
      printf("\n 要修改的结点没有找到 no=%d", heroNode.no)
    }

  }

  //遍历链表
  /*
  思路
  1. 使用temp 指针帮助遍历，不能使用head
  2. 定义方法，判断链表是否为空
  3. temp = head.next , 从第一个有效节点遍历
   */

  def isEmpty(): Boolean = {
    head.next == null
  }

  def list(): Unit = {
    if(isEmpty()) {
      println("链表空")
      return
    }

    var temp = head.next
    breakable {
      //遍历
      while (true) {
        //输出节点信息
        printf("\n no=%d name=%s nickname=%s", temp.no, temp.name, temp.nickname)
        //判断是否为空
        if (temp.next == null) {
          break()
        }
        //后移temp
        temp = temp.next
      }
    }
  }

  //添加节点
  def addHeroNode(heroNode: HeroNode): Unit = {

    //定义辅助指针
    var temp = head

    //定位到链表最后
    breakable {
      while (true) {

        if (temp.next == null) {
          break();
        }
        //后移
        temp = temp.next
      }
    }
    //这时temp 指向最后节点
    temp.next = heroNode
  }
}


//创建节点 HeroNode
class HeroNode(hNO:Int, hName:String, hNickname:String) {
  //data
  val no = hNO
  var name = hName
  var nickname = hNickname
  //next
  var next : HeroNode = null
}
