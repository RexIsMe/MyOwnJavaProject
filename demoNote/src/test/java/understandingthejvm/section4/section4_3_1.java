package understandingthejvm.section4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rex
 * @title: section4_3_1
 * @projectName demoNote
 * @description: TODO
 * @date 2019/8/198:29
 */
public class section4_3_1 {


    public static void main(String[] args) throws Exception{
        /**
         * -Xms100m -Xmx100m -XX:+UseSerialGC
         */
        Thread.sleep(3000);
        fillHeap(1000);
        System.gc();

    }



    static class OOMObject{
        public byte[] placeholder = new byte[16 * 1024];

    }


    public static void fillHeap(int num) throws InterruptedException{
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
        }
//        System.gc();
    }

}
