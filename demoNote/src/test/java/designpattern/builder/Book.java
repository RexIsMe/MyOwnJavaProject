package designpattern.builder;

/**
 * <p>Title: Book</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/4 9:37
 */

public class Book extends Product {
    private int price=0;
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
