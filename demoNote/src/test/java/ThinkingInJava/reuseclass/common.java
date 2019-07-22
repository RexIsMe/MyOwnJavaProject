package ThinkingInJava.reuseclass;

/**
 * @author Rex
 * @title: common
 * @projectName demoNote
 * @description: TODO
 * @date 2019/6/512:46
 */
public class common {

    public static void main(String[] args) {
        C c = new C();
    }

}



class A{

    public A() {
        System.out.println("A constractor");
    }
}

class B{

    public B() {
        System.out.println("B constractor");
    }
}


class C extends A{

    private B b;
}
