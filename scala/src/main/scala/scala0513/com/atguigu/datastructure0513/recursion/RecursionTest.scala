package scala0513.com.atguigu.datastructure0513.recursion

object RecursionTest {
  def main(args: Array[String]): Unit = {
    test(4) //
  }
  def test(n: Int): Unit = {
    if (n > 2) {
      test(n - 1)
    } else {
      println("n=" + n)
    }
  }


}

