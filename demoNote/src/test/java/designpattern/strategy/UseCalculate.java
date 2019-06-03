package designpattern.strategy;

/**
 * 计算的使用类
 * @author Rex
 */
public class UseCalculate {

    private Calculate calculate;

    public UseCalculate(Calculate calculate) {
        this.calculate = calculate;
    }

    public String cal(int a, int b){
        return calculate.cal(a, b);
    }


    public static void main(String[] args) {

//        UseCalculate useCalculate = new UseCalculate(new AddCalculate());
        UseCalculate useCalculate = new UseCalculate(new Subtraction());


        String cal = useCalculate.cal(1, 2);
        System.out.println(cal);


    }

}
