package ThinkingInJava.InitializeAndClear;

/**
 * @author Rex
 * @title: Practice
 * @projectName demoNote
 * @description: TODO
 * @date 2019/6/317:04
 */
public class Practice {

    public static void main(String[] args) {

        TestFinalize testFinalize = new TestFinalize(true);
        testFinalize = null;
//        testFinalize.checkIn();

//        new TestFinalize(true);
        System.gc();
    }

}

class TestFinalize{

    boolean checkOut = false;
    TestFinalize(boolean checkOut){
        this.checkOut = checkOut;
    }
    void checkIn(){
        this.checkOut = false;
    }
    protected void finalize(){
        if(checkOut)
        System.out.println("say something");
    }

}