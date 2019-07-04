package ThinkingInJava;

public class OOMAndSOF {

    //记录local jvm虚拟机栈的栈帧深度
    private static int ii = 0;

    /**
     * 执行后会报StackOverflowError错误的方法
     */
    public static void SOF() { //被调用时开辟一个栈帧
        ii++;
        System.out.println(ii);
        SOF();
    }

    /**
     * 执行后会报OutOfMemoryError错误的方法
     */
    public static void OOM() {
        String str = "OOM";
        for (int i = 0; i < 1000; i++) {
            System.out.println(ii += str.length());
            str = str + str;
        }
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        //执行该方法，报错：StackOverflowError
//		SOF();
        //执行该方法，报错：OutOfMemoryError
        OOM();

    }

}
