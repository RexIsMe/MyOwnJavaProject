package ThinkingInJava.classmessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>Title: StaticDynamic</p>
 * <p>Description: 静态代理</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/2 9:17
 */

public class StaticDynamic {

    public static void main(String[] args){
        StaticPetProxy staticPetProxy = new StaticPetProxy();
        Pet pet = staticPetProxy.newInstance(new Dog());
        pet.eat();
        pet.play();
    }


}

class StaticPetProxy implements InvocationHandler {

    private Pet targetProxy;

    public Pet newInstance(Pet proxy) {
        this.targetProxy = proxy;
        return (Pet)Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy did " + method.getName());
        Object invoke = method.invoke(targetProxy, args);
        System.out.println("proxy did "  + method.getName() + " after");
        return invoke;
    }
}