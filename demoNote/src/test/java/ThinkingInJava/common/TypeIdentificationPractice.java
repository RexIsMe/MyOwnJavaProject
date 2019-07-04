package ThinkingInJava.common;

import ThinkingInJava.classmessage.Practice;

/**
 * <p>Title: TypeIdentificationPractice</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/2 19:37
 */

public class TypeIdentificationPractice {

    public static void main(String[] args){
        Practice practice = new Practice();
        practice.pu();

    }
    @org.junit.Test
    public void haha(){
        Practice p = new Practice();
        p.pu();

        Test testPractice = new Test();
//        testPractice.d();  //compile error
        testPractice.pu();
//        testPractice.pro();
        testPractice.testst();
    }

}

class Test extends Practice{

    public void testst(){
        super.pu();
        super.pro();
    }

}