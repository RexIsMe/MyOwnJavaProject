package designpattern.builder;

/**
 * <p>Title: Movie</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/4 9:36
 */

public class Movie extends Product{
    private String actor;//主演
    private String director;//导演
    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
}
