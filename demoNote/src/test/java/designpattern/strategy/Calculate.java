package designpattern.strategy;

/**
 * 计算的抽象类
 * @author Rex
 */
public interface Calculate {

    String cal(int a, int b);
    String cal(double a, double b);

}
