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

        TestFinalize testFinalize = new TestFinalize();

    }

}

class TestFinalize{

    protected void finalize(){
        System.out.println("say something");
    }

}