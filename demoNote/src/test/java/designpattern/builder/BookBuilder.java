package designpattern.builder;

/**
 * <p>Title: BookBuilder</p>
 * <p>Description: Book对象的构建逻辑具体实施</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/4 9:39
 */
public class BookBuilder implements Builder {
    public Product book=new Book();//这里是上转型对象的使用
    public void recruiting() {
        book.setAuthor("J.K.罗琳著小说");
    }
    public void writeScript() {
        book.setWorkName("哈利·波特与魔法石");
    }
    public void raiseFunds() {
        book.setFunds(10000);
    }
    public void propagate() {
        book.setContent("最畅销的的魔幻小说！");
    }
    public void released() {
        book.setWorkType("小说");
    }
    public Product buildProduct() {
        return book;
    }
}
