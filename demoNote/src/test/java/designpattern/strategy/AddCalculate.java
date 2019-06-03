package designpattern.strategy;

/**  
* @title: 
* @projectName 
* @description: TODO
* @author 99686
* @date 2019/6/3 15:35
*/
public class AddCalculate implements Calculate {

    @Override
    public String cal(int a, int b) {
        return String.valueOf(a + b);
    }

    @Override
    public String cal(double a, double b) {
        return String.valueOf(a + b);
    }
}
