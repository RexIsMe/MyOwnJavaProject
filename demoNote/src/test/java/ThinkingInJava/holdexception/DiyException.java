package ThinkingInJava.holdexception;

import org.junit.Test;

/**
 * <p>Title: DiyException</p>
 * <p>Description: 自定义异常联系</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/24 10:11
 */
public class DiyException {

    public static void main(String[] args){



    }

    @Test
    public void test(){
        try{
            throw new Exception("自定义异常练习");
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            System.out.println("finally executed");
        }
    }

    @Test
    public void test1(){
        try{
            MyException myException = new MyException("自定义异常练习myException");
            myException.getMessageString();
            throw myException;
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            System.out.println("finally executed");
        }
    }


    /**
     * 使用while循环建立类似“回复模型”的异常处理行为，它将不断重复，知道异常不再抛出
     */
    @Test
    public void test2(){
        int i = 0;
        int j = 0;
        int[] iArr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        boolean flag = true;

        while(flag){
            try{
                flag = false;
                j = iArr[i++];
                if(j == 0){
                    MyException myException = new MyException("自定义异常练习myException");
                    myException.getMessageString();
                    throw myException;
                }
            } catch (Exception e){
                flag = true;
                System.out.println(e.getMessage());
            } finally {
                System.out.println("finally executed");
            }
        }

    }

    /**
     * 使用标记代替while,优化代码
     *
     * 标记只能在循环里用......
     */
    @Test
    public void test3(){
        int i = 0;
        int j = 0;
        int[] iArr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        boolean flag = true;
        out:
        while(flag)
            try {
                j = iArr[i++];
                if (j == 0) {
                    MyException myException = new MyException("自定义异常练习myException");
                    myException.getMessageString();
                    throw myException;
                }
                flag = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
//                break out;
                continue out;
            } finally {
                System.out.println("finally executed");
            }

    }

}


class MyException extends RuntimeException{

    private String message;

    public MyException() {
    }

    public MyException(String message) {
        super(message);
        this.message = message;
    }

    public void getMessageString(){
        System.out.println(message);
    }
}
