package designpattern.builder;

/**
 * <p>Title: CommonTest</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/4 9:40
 */

public class CommonTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Director director=new Director();//指挥者角色
        //一部电影的产生
        MovieBuilder movieBuilder=new MovieBuilder();
        Product movie=director.bulidProduct(movieBuilder);
        System.out.println(movie.printString());
        // 一部小说的产生
        BookBuilder bookBuilder=new BookBuilder();
        Product book=director.bulidProduct(bookBuilder);
        System.out.println(book.printString());
    }

}
