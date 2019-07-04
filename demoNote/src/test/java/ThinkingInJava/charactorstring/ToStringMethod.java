package ThinkingInJava.charactorstring;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ToStringMethod</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/27 14:21
 */
public class ToStringMethod {

    @Test
    public void test(){
        List<InfiniteRecursion> li =  new ArrayList<>();
        for(int i = 0; i < 10; i++){
            li.add(new InfiniteRecursion());
        }
        System.out.println(li);
    }

    @Test
    public void test2(){
        List<InfiniteRecursionFix> li =  new ArrayList<>();
        for(int i = 0; i < 10; i++){
            li.add(new InfiniteRecursionFix());
        }
        System.out.println(li);
    }

    @Test
    public void test3(){
        int i = 0;
        while(true){
            String string = new String();
            System.out.println(i++);
        }
    }


}


class InfiniteRecursion{
    /**
     * 这里this对象会因为被动要转换成String而调用toString()方法，
     * 造成了递归调用，最终导致堆栈溢出错误
     * @return
     */
    public String toString(){
        return " InfiniteRecursion address: " + this + "\n";
    }

}

class InfiniteRecursionFix{
    public String toString(){
        return " InfiniteRecursion address: " + super.toString() + "\n";
    }
}