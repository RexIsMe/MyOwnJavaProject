package designpattern.builder;

/**
 * <p>Title: Builder</p>
 * <p>Description: 对象的构建逻辑描述
 *  * 影视，书籍等作品创建步骤</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/7/4 9:37
 */
public interface Builder {
    void recruiting();//人员招募
    void writeScript();//写剧本，书
    void raiseFunds();//筹集资金
    void propagate();//宣传
    void released();//出版，发行，上映
    Product buildProduct();
}
