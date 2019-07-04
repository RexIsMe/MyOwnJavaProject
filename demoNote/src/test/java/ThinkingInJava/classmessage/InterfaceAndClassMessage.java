package ThinkingInJava.classmessage;

/**
 * <p>Title: InterfaceAndClassMessage</p>
 * <p>Description: 接口与类型信息</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/2 19:21
 */

public class InterfaceAndClassMessage {
    
    public static void main(String[] args) {
        A a = new B();
        a.f();

        System.out.println(a.getClass().getName());

        if(a instanceof B){
            B b = (B)a;
            b.g();
        }
    }
    
}


interface A{
    void f();
}

class B implements A{

    @Override
    public void f() {
        System.out.println("method f()");
    }

    public void g(){
        System.out.println("method g()");
    }
}

