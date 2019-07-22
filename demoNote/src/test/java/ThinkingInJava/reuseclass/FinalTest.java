package ThinkingInJava.reuseclass;

/**
 * @author Rex
 * @title: FinalTest
 * @projectName demoNote
 * @description: static final 和 final 的区别
 * @date 2019/6/519:10
 */
public class FinalTest {

    public static final String str1 = "ssdf";
    public final String str2 = "ssdf";


    public static void main(String[] args) {
        FinalTest finalTest1 = new FinalTest();
        FinalTest finalTest2 = new FinalTest();
        System.out.println(finalTest1.str2);
        System.out.println(finalTest2.str2);
    }

}
