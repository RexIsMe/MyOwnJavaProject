package ThinkingInJava.reuseclass;

import org.junit.Test;

/**
 * <p>Title: BaseClass1</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Administrator
 * @version V1.0
 * @date 2019/6/24 17:17
 */

public class BaseClass1 extends BaseClass2 {

    public void ok(){
        System.out.println("base class 1");
    }

    public static void main(String[] args){


    }

    @Test
    public void test(){
        BaseClass1 b1 = new BaseClass1();
        b1.ok();
        BaseClass1.super.ok();
    }
    
}
