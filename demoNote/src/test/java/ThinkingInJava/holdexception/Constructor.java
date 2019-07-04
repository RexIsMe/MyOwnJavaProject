package ThinkingInJava.holdexception;

/**
 * <p>Title: Constructor</p>
 * <p>Description: 异常处理--构造器</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/24 17:51
 */

public class Constructor {

    public static void main(String[] args) {
        // Section 1:
        NeedsCleanup nc1 = new NeedsCleanup();
        try{
            // ...
        } finally {
            nc1.dispose();
        }

        //Section 2:
        //If construction cannot fail you can group objects:
        NeedsCleanup nc2 = new NeedsCleanup();
        NeedsCleanup nc3 = new NeedsCleanup();
        try{
            //...
        } finally {
            nc3.dispose();//Reverse order of construction
            nc2.dispose();
        }


        //Section 3
        //If construction cannot fail you must guard each one
        try{
            NeedsCleanup2 nc4 = new NeedsCleanup2();
            try{
                NeedsCleanup2 nc5 = new NeedsCleanup2();
                try{
                    //...
                } finally{
                    nc5.dispose();
                }
            } catch (ConstructionException e) { // nc5 constructor
                System.out.println(e);
            } finally {
                nc4.dispose();
            }
        } catch (ConstructionException e){ // nc4 constructor
            System.out.println(e);
        }

    }
}


class NeedsCleanup{
    //Construction can't fail

    private static long counter = 1;
    private final long id = counter++;
    public void dispose(){
        System.out.println("Needs Cleanup " + id + " disposed");
    }
}

class ConstructionException extends Exception{
}

class NeedsCleanup2 extends NeedsCleanup{
    //Construction can fail

    public NeedsCleanup2() throws ConstructionException{}
}
