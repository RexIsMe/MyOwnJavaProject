package ThinkingInJava.holdexception.limit;

/**
 * <p>Title: LimitOfException</p>
 * <p>Description: 异常限制</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/24 14:43
 */
public class LimitOfException {



}


class BaseballException extends Exception{}
class Foul extends BaseballException{}
class Strike extends BaseballException{}

abstract class Inning{
    public Inning() throws BaseballException{}
    public void event() throws BaseballException{
        //Doesn't actually have to throw anything
    }
    public abstract void atBat() throws Strike, Foul;
    public void walk(){}//Throws no checked exceptions
}

class StormException extends Exception{}
class RainedOut extends StormException{}
class PopFoul extends Foul{}

interface Storm{
    void event() throws RainedOut;
    void rainHard() throws RainedOut;
}



