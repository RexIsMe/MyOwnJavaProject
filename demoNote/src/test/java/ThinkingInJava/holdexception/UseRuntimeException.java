package ThinkingInJava.holdexception;

import org.junit.Test;

/**
 * <p>Title: RuntimeException</p>
 * <p>Description: 巧用RuntimeException</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/25 9:17
 */
public class UseRuntimeException {

    @Test
    public void test(){
        try{
            try{
                throw new ClassCastException();
            }catch (Exception e){
                throw new java.lang.RuntimeException(e);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
