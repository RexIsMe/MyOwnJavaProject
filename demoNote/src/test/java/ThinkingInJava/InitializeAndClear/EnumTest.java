package ThinkingInJava.InitializeAndClear;

/**
 * @author Rex
 * @title: EnumTest
 * @projectName demoNote
 * @description: TODO
 * @date 2019/6/49:19
 */
public class EnumTest {

    public static void main(String[] args) {

        for (Money m :
                Money.values()) {

            System.out.println("val:" + m + ", ordnal:" + m.ordinal());

            switch (m){
                case ONE_YUAN :
                    System.out.println("一元人民币");
                    break;
                case TWO_YUAN :
                    System.out.println("两元人民币");
                    break;
                case FIVE_YUAN :
                    System.out.println("五元人民币");
                    break;
                case TEN_YUAN :
                    System.out.println("十元人民币");
                    break;
                case TWENTY_YUAN :
                    System.out.println("二十元人民币");
                    break;
                case FIFTY_YUAN :
                    System.out.println("五十元人民币");
                    break;
                default:
                    System.out.println("nnn");
            }


        }




    }


}

enum Money{
    ONE_YUAN,
    TWO_YUAN,
    FIVE_YUAN,
    TEN_YUAN,
    TWENTY_YUAN,
    FIFTY_YUAN;

}