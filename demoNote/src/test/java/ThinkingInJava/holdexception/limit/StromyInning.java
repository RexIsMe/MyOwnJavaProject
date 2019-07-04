package ThinkingInJava.holdexception.limit;

/**
 * <p>Title: StromyInning</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Administrator
 * @version V1.0
 * @date 2019/6/24 14:51
 */

public class StromyInning extends Inning implements Storm {
    //OK TO ADD NEW EXCEPTIONS FOR CONSTRUCTIRS, BUT YOU MUST DEAL WITH THE BASE CONSTRUCTOR EXCEPTIONS
    public StromyInning() throws RainedOut,BaseballException {
    }

    public StromyInning(String s) throws Foul,BaseballException {
    }

    //Regular methods must conform to base class:
    // Compile error
    //! void walk() throws PopFoul{}

    //Interface CANNOT add exceptions to existing methods from the base class

    //!public void event() throws RainedOut{}
    //If the method doesn't already exist in the base class. the exception id OK
    @Override
    public void rainHard() throws RainedOut {

    }

    //You can choose to not throw any exceptions, even if the base version does
    public void event(){}

    //Overridden methods can throw inherited exceptions
    @Override
    public void atBat() throws PopFoul {

    }

//    @Override
//    public void atBat() throws Strike, Foul {
//
//    }
    
    public static void main(String[] args){
        try{
            StromyInning si = new StromyInning();
            si.atBat();
        } catch (PopFoul e){
            System.out.println("Pop foul");
        } catch (RainedOut e){
            System.out.println("Rained Out");
        } catch (BaseballException e){
            System.out.println("generic Baseball Exception");
        }

        //Strike not thrown in derived version
        try{
            //what happens if you upcast?
            Inning i = new StromyInning();
            i.atBat();
            //You must catch the exceptions from the base-class version of the method
        } catch (Strike e){
            System.out.println("Strike");
        } catch (Foul e){
            System.out.println("Foul");
        } catch (RainedOut e){
            System.out.println("Rained Out");
        } catch (BaseballException e){
            System.out.println("generic Baseball Exception");
        }
    }

}
