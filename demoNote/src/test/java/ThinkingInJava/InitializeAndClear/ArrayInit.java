package ThinkingInJava.InitializeAndClear;

/**
 * @author Rex
 * @title: ArrayInit
 * @projectName demoNote
 * @description: TODO
 * @date 2019/6/48:59
 */
public class ArrayInit {

    public void overloading1(float f, String... strings){
        System.out.println("1");
    }
    public void overloading1(float f){
        System.out.println("2");
    }
    public void overloading1(String... strings){
        System.out.println("3");
    }

    public static void main(String[] args) {

        ArrayInit arrayInit = new ArrayInit();
        arrayInit.overloading1(1f);
//        arrayInit.overloading1("12", "123");

        Type1[] type1s;
        type1s = new Type1[]{new Type1("123")};

    }

}


class Type1{

    public Type1(String str) {
        System.out.println(str);

    }
}
