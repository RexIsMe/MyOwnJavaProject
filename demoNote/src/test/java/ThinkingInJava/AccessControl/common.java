package ThinkingInJava.AccessControl;

import ThinkingInJava.AccessControl.debug.DebugTest;

/**
 * @author Rex
 * @title: common
 * @projectName demoNote
 * @description: TODO
 * @date 2019/6/59:15
 */
public class common {

    public static void main(String[] args) {



    }

    /**
     * 条件编译测试
     * （好像是在不同时候调用不同包中的同名类）
     */
    public void test1(){
        DebugTest debugTest = new DebugTest();
        ThinkingInJava.AccessControl.debugoff.DebugTest debugTest1 = new ThinkingInJava.AccessControl.debugoff.DebugTest();
        debugTest.debug();
        debugTest1.debug();
    }


}
