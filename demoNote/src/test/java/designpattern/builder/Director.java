package designpattern.builder;

/**
 * <p>Title: Director</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/4 9:39
 */
/**
 * 指挥者角色
 * @author lushuaiyin
 *
 */
public class Director {
    public Product bulidProduct(Builder builder){
        builder.raiseFunds();//筹集资金
        builder.recruiting();//招募人员
        builder.propagate();//宣传
        builder.released();//发行，上映
        return builder.buildProduct();
    }
}

