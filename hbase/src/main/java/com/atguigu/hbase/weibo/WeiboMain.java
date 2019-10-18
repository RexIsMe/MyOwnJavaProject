package com.atguigu.hbase.weibo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author Rex
 * @title: WeiboMain
 * @projectName hbase
 * @description: TODO
 * @date 2019/8/1710:20
 */
public class WeiboMain {

    private static Connection connection;
    private static Admin admin;
    private static final String ZK_IP = "192.166.9.102";
    private static final String ZK_PORT = "2181";
    //微博内容表的表名
    private static final byte[] TABLE_CONTENT = Bytes.toBytes("weibo:content");
    //用户关系表的表名
    private static final byte[] TABLE_RELATIONS = Bytes.toBytes("weibo:relations");
    //微博收件箱表的表名
    private static final byte[] TABLE_RECEIVE_CONTENT_EMAIL = Bytes.toBytes("weibo:receive_content_email");

    static {
        initConnection();
    }

    public static void initConnection(){
        if(connection != null){
            return;
        }

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", ZK_IP);
        conf.set("hbase.zookeeper.property.clientPort", ZK_PORT);
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNameSpace() throws IOException {
        Admin admin = connection.getAdmin();
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("weibo").
                addConfiguration("creator", "Jinji").
                addConfiguration("create_time", System.currentTimeMillis() + "").
                build();
        admin.createNamespace(namespaceDescriptor);
        admin.close();
    }


    public void createTables(HTableDescriptor... hTableDescriptors) throws IOException {
        Admin admin = connection.getAdmin();

        for (int i = 0; i < hTableDescriptors.length; i++) {
            admin.createTable(hTableDescriptors[i]);
        }

        admin.close();
    }

    /**
     * 创建微博内容表
     * Table Name:weibo:content
     * RowKey:用户ID_时间戳
     * ColumnFamily:info
     * ColumnLabel:标题	内容		图片URL
     * Version:1个版本
     */
    public HTableDescriptor setTableContent(){
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_CONTENT));
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("info");
        hColumnDescriptor.setBlockCacheEnabled(true);
        hColumnDescriptor.setBlocksize(2097152);
        hColumnDescriptor.setMinVersions(1);
        hColumnDescriptor.setMinVersions(1);
        hColumnDescriptor.setCom
        hTableDescriptor.addFamily(hColumnDescriptor);
    }

    public void connectionClose(){
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
