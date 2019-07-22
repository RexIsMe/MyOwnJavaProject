package ThinkingInJava.holdexception;

import org.junit.Test;

/**
 * <p>Title: LostException</p>
 * <p>Description: 异常丢失</p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/24 14:00
 */
public class LostException {

    void f() throws VeryImportantException{
        throw new VeryImportantException();
    }

    void g() throws HoHumException{
        throw new HoHumException();
    }

    public static void main(String[] args){

        /**
         * finally中抛出异常，导致将try中抛出的异常覆盖-->丢失了
         */
        try{
            LostException lostException = new LostException();
            try{
                lostException.f();
            } finally {
                lostException.g();
            }
        } catch (Exception e){
            System.out.println(e);
        }

    }

    @Test
    public void test(){
        try{
            try{
                throw new VeryImportantException();
            } finally {
                return;
            }
        } catch (Exception e){
            System.out.println("i get u");
        }
    }


}


class VeryImportantException extends Exception{

    @Override
    public String toString() {
        return " A Very Important Exception ";
    }
}

class HoHumException extends Exception{

    @Override
    public String toString() {
        return " A HoHum Exception ";
    }
}