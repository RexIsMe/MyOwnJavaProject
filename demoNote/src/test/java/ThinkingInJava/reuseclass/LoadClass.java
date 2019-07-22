package ThinkingInJava.reuseclass;

/**
 * @author Rex
 * @title: LoadClass
 * @projectName demoNote
 * @description: 类的加载
 * @date 2019/6/519:35
 */
public class LoadClass {

    public static void main(String[] args) {

        /**
         * 对于继承的情形，每次创建对象时默认都会先创建一个父类对象
         * 而在类中，各个部分的执行顺序：先执行代码块，再构造器
         */
        new child();
        new child();

    }


}

class parents{

    public parents() {
        System.out.println("parents constructor");
    }

    static {
        System.out.println("parents static code room");
    }

    {
        System.out.println("parents code room");
    }

}

class child extends parents{

    public child() {
        System.out.println("child constructor");
    }

    static {
        System.out.println("child static code room");
    }

    {
        System.out.println("child code room");
    }
}

