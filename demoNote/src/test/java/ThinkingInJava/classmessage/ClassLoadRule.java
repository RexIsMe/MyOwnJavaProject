package ThinkingInJava.classmessage;

import java.util.Random;

/**
 * <p>Title: ClassLoadRule</p>
 * <p>Description: 类被加载的时刻和方式</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/1 9:33
 */

public class ClassLoadRule {

    public static Random rand = new Random(47);
    public static void main(String[] args){
        Class initable = Initable.class;
        System.out.println("After creating Initable ref");

        //Does not trigger initialization
        System.out.println(Initable.staticFinal);

        //Does trigger initialization
        System.out.println(Initable.staticFinal2);

        //Does trigger initialization
        System.out.println(Initable2.staticNonFinal);

        try {
            Class initable3 = Class.forName("ThinkingInJava.classmessage.Initable3");
            System.out.println("After creating Initable3 ref");
            System.out.println(Initable3.staticNonFinal);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}


class Initable{
    static final int staticFinal = 47;
    static final int staticFinal2 = new Random().nextInt(1000);

    static{
        System.out.println("Initializing Initable");
    }
}

class Initable2{
    static int staticNonFinal = 147;

    static {
        System.out.println("Initializing Initable2");
    }
}

class Initable3{
    static int staticNonFinal = 74;

    static {
        System.out.println("Initializing Initable3");
    }
}

