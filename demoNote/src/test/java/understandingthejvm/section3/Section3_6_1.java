package understandingthejvm.section3;

import org.junit.Test;

/**
 * @author Rex
 * @title: Section3_6_1
 * @projectName demoNote
 * @description: TODO
 * @date 2019/8/188:48
 */
public class Section3_6_1 {


    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {

//        testAllocation();
//        testPretenureSizeThreshold();
        testTenuringThreshold();
    }

    /**
     * 测试新生代因为内存不够分配而触发GC操作
     *
     * VM 参数 ： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void testAllocation(){
        byte[] allocation1, allocation2,allocation3,allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }


    /**
     * 测试PretenureSizeThreshold参数，当大对象需要的内存大于该配置的值时就直接放到老年代，避免了该对象在新生代中的来回复制（Parallel Scavenge没有该参数，需自己指定其他垃圾收集器）
     *
     * VM 参数 ： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseParNewGC -XX:PretenureSizeThreshold=3145728
     * -XX:+UseParNewGC 指定使用Par New + Serail Old GC
     */
    public static void testPretenureSizeThreshold(){
        byte[] allocation4;
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * 测试长期存活的对象进入老年代，可以通过参数MaxTenuringThreshold参数指定
     *
     * VM 参数 ： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    public static void testTenuringThreshold(){
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB * 4];
        allocation3 = new byte[_1MB * 4];
        allocation3 = new byte[_1MB * 4];
        System.out.println("11111");
        allocation3 = null;
        System.out.println("22222");
        allocation3 = new byte[_1MB * 4];
        System.out.println("33333");

    }



}
