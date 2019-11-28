package com.atguigu.hbase.weibo;

import java.util.List;

public class WeiboTest {

    public static void testPublishContent(WeiboMain wb) {
        wb.publishContent("0001", "今天买了一包空气，送了点薯片，非常开心！！");
        wb.publishContent("0001", "今天天气不错。");
        wb.publishContent("0008", "准备下课！");
        wb.publishContent("0009", "准备关机！");

    }

    public static void testAddAttend(WeiboMain wb) {
        wb.addAttends("0001", "0008", "0009");

    }

    public static void testRemoveAttend(WeiboMain wb) {
        wb.removeAttends("0001", "0008");
    }

    public static void testShowMessage(WeiboMain wb) {
        List<Message> messages = wb.getAttendContent("0001");
        for(Message message : messages){
            System.out.println(message);
        }

    }

    public static void main(String[] args) {
        WeiboMain wb = new WeiboMain();
        wb.connect();
//        wb.initNamespace();
//        wb.createTableContent();
//        wb.createTableRelations();
//        wb.createTableReceiveContentEmail();

//        testPublishContent(wb);

//        testAddAttend(wb);

        testShowMessage(wb);

//        System.out.println("========================================");
//
//        testRemoveAttend(wb);
//
//        testShowMessage(wb);


        wb.close();
    }

}
