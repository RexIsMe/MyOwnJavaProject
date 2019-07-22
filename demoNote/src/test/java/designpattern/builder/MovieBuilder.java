package designpattern.builder;

/**
 * <p>Title: MovieBuilder</p>
 * <p>Description: Movie对象的构建逻辑具体实施</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/4 9:38
 */
public class MovieBuilder implements Builder {
    public Product movie=new Movie();//这里是上转型对象的使用
    /*
     假如我们创建的对象只关心行为特征，那么就可以使用父类或者接口来声明对象。
    这样就不会在创建的时候就不会涉及到具体对象类的其他私有属性。
    如果我们在创建的时候，关心这些属性。那么就直接使用子类new出对象。
    Movie movie=new Movie();
     */
    public void recruiting() {
        movie.setAuthor("詹姆斯·卡梅隆");
    }
    public void writeScript() {
        movie.setWorkName("阿凡达");
    }
    public void raiseFunds() {
        movie.setFunds(200000);
    }
    public void propagate() {
        movie.setContent("首部3D科幻巨作！");
    }
    public void released() {
        movie.setWorkType("3D电影");
    }
    public Product buildProduct() {
        return movie;
    }
}
