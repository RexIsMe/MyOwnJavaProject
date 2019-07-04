package ThinkingInJava.classmessage;

import org.junit.Test;

/**
 * <p>Title: Common</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/1 9:32
 */

public class Common {

    @Test
    public void test1(){
        Practice practice = new Practice();
        practice.pu();
        practice.d();
        practice.pro();
    }


    /**
     * 泛型
     */
    @Test
    public void test(){
        Class intClass = int.class;
        Class<Integer> genericIntClass = int.class;

        genericIntClass = Integer.class;
        intClass = double.class;
        //illegal
        //genericIntClass = double.class;

        /**
         * 通配符 ？
         */
        Class<?> genericIntClass2 = int.class;
        genericIntClass2 = double.class;
    }

    @org.junit.Test
    public void haha(){
        Practice p = new Practice();
        p.pu();

        TestPractice testPractice = new TestPractice();
        testPractice.d();
        testPractice.pu();
        testPractice.pro();
    }

}

class TestPractice extends Practice{

}

