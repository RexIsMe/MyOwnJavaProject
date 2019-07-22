package ThinkingInJava.charactorstring;

/**
 * <p>Title: Common</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/27 11:15
 */
public class Common {
    
    public void test(String[] fields){
        String str = "";
        for(int i = 0, len = fields.length; i < len; i++){
            str += fields[i];
        }
    }

    public void testJavaP(String[] fields){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0, len = fields.length; i < len; i++){
            stringBuilder.append(fields[i]);
        }
    }

    public static void main(String[] args){
        System.out.println("123");
    }
    
}
