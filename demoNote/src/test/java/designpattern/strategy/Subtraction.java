package designpattern.strategy;

/**
 * 减法运算
 * @author Rex
 */
public class Subtraction implements Calculate{


    @Override
    public String cal(int a, int b) {
        return String.valueOf(a - b);
    }

    @Override
    public String cal(double a, double b) {
        return String.valueOf(a - b);
    }
}
