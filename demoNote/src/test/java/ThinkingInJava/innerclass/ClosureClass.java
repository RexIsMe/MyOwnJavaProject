package ThinkingInJava.innerclass;

import java.io.*;
import java.util.List;

import static ThinkingInJava.innerclass.StaticPrint.myPrint;

/**
 * <p>Title: Closure</p>
 * <p>Description: 通过内部类达到闭包功能 实现类型C语言中的指针效果</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/15 13:56
 */

public class ClosureClass {

    public static void main(String[] args){
        Callee1 c1 = new Callee1();
        Callee2 c2 = new Callee2();
        MyIncrement.f(c2);
        Caller caller1 = new Caller(c1);
        Caller caller2 = new Caller(c2.getCallbackReference());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();


    }


}


class StaticPrint extends PrintStream{

    public StaticPrint(OutputStream out) {
        super(out);
    }

    public static void myPrint(Object object){
        System.out.println(object);
    }




}

interface Incrementable{
    void increment();
}

//very simple to just implement the interface
class Callee1 implements  Incrementable{
    private int i = 0;

    @Override
    public void increment() {
        i++;
        myPrint(i);
    }
}

class MyIncrement{
    public void increment() {
        myPrint("Other operation");
    }
    static void f(MyIncrement mi){
        mi.increment();
    }
}

/**
 * If your class must implement increment() in some other way. you must use an inner class
 */
class Callee2 extends MyIncrement{
    private int i = 0;
    public void increment(){
        super.increment();
        myPrint(i++);
    }

    private class Closure implements Incrementable{
        @Override
        public void increment() {
            //Specify outer-class method, otherwise you'd get an infinite recursion
            Callee2.this.increment();
        }
    }

    Incrementable getCallbackReference(){
        return new Closure();
    }
}

class Caller{
    private Incrementable callbackReference;
    Caller(Incrementable cbh){
        callbackReference = cbh;
    }
    void go(){
        callbackReference.increment();
    }
}



