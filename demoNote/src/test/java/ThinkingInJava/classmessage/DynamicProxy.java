package ThinkingInJava.classmessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>Title: DynamicProxy</p>
 * <p>Description: 动态代理</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/1 20:21
 */

public class DynamicProxy {

    public static void main(String[] args){
        PetProxy petProxy = new PetProxy();
        Pet pet = (Pet)petProxy.newInstance(new Dog());
        pet.play();
        pet.eat();
    }


}

class PetProxy implements InvocationHandler {

    private Object targetProxy;

    public Object newInstance(Object proxy) {
        this.targetProxy = proxy;
        return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
    }

    /*
     * 1、打断点debug运行时会调用toString()多打印一些东西
     * proxy did toString
     * proxy did toString after
     *
     * 2、为什么debug且打断点时会执行toString()?
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy did " + method.getName());
        System.out.println("targetProxy：" + targetProxy.toString());
        System.out.println("proxy：" + proxy.toString());
        Object invoke = method.invoke(targetProxy, args);
        System.out.println("proxy did "  + method.getName() + " after");
        return invoke;
    }
}

interface Pet{
    void eat();
    void play();
}

class Dog implements Pet{
    @Override
    public void eat() {
        System.out.println("Dog eat");
    }

    @Override
    public void play() {
        System.out.println("Dog play");
    }

    @Override
    public String toString() {
        System.out.println("Dog toString()");
//        return super.toString();
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}

class Cat implements Pet{
    @Override
    public void eat() {
        System.out.println("Cat eat");
    }

    @Override
    public void play() {
        System.out.println("Cat play");
    }
}