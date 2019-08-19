package ThinkingInJava.dealerrorbyexception;

import com.example.demo.exception.ThreeTimesException;
import org.junit.Test;

/**
 * @author Rex
 * @title: finallyalwayswork
 * @projectName demoNote
 * @description: TODO
 * @date 2019/7/2221:52
 */
public class finallyalwayswork {

    public static void main(String[] args) {



    }


    /**
     * 查看test1和test2的执行结果，发现第一次判断时都是成立的，
     * TODO
     * 由此推断，对于count++操作，count的赋值操作是在==比较操作之后的，具体流程这个还不知道怎么验证
     */
    @Test
    public void test1(){
        int count = 0;
        while (true){
            try {
//                if(count++ == 0){
//                    throw new ThreeTimesException("ThreeException");
//                }
                int s = 0;
                if((s = ++count) == 0){
                    throw new ThreeTimesException("ThreeException");
                }
                System.out.println("no exception");
            } catch (ThreeTimesException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("finally work");
                if(count == 2){
                    break;
                }
            }
        }
    }

    @Test
    public void test2(){
        int count = 0;
        while (true){
            try {
                if(0 == count++){
                    throw new ThreeTimesException("ThreeException");
                }
                System.out.println("no exception");
            } catch (ThreeTimesException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("finally work");
                if(count == 2){
                    break;
                }
            }
        }
    }

}
