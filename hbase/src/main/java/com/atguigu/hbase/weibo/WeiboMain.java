package com.atguigu.hbase.weibo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 谷粒微博
 */
public class WeiboMain {

    //常用设置
    private Connection connection;

    //微博内容表的表名
    private static final byte[] TABLE_CONTENT = Bytes.toBytes("weibo:content");
    //用户关系表的表名
    private static final byte[] TABLE_RELATIONS = Bytes.toBytes("weibo:relations");
    //微博收件箱表的表名
    private static final byte[] TABLE_RECEIVE_CONTENT_EMAIL = Bytes.toBytes("weibo:receive_content_email");

    //建立连接
    public void connect() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");

        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //初始化命名空间
    public void initNamespace() {
        Admin admin = null;
        try {
            admin = connection.getAdmin();

            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("weibo").
                    addConfiguration("creator", "Atguigu").
                    addConfiguration("create_time", Long.toString(System.currentTimeMillis())).
                    build();

            admin.createNamespace(namespaceDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 建立微博内容表
     * rowkey：用户ID_时间戳
     * 列族：info
     * 限定名：content
     * 版本：1
     */
    public void createTableContent() {
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            //建表
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_CONTENT));

            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("info");

            //快缓存开启
            hColumnDescriptor.setBlockCacheEnabled(true);
            //块缓存大小
            hColumnDescriptor.setBlocksize(2097152);

            //设置版本
            hColumnDescriptor.setMinVersions(1);
            hColumnDescriptor.setMaxVersions(1);


            hTableDescriptor.addFamily(hColumnDescriptor);

            admin.createTable(hTableDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 建立微博关系表
     * rowkey：用户ID
     * 列族：attends和fans
     * 限定名：用户ID
     * 值：用户ID
     * 版本：1
     */
    public void createTableRelations() {
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            //建表
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_RELATIONS));

            HColumnDescriptor attends = new HColumnDescriptor("attends");

            //快缓存开启
            attends.setBlockCacheEnabled(true);
            //块缓存大小
            attends.setBlocksize(2097152);

            //设置版本
            attends.setMinVersions(1);
            attends.setMaxVersions(1);


            hTableDescriptor.addFamily(attends);

            HColumnDescriptor fans = new HColumnDescriptor("fans");

            //快缓存开启
            fans.setBlockCacheEnabled(true);
            //块缓存大小
            fans.setBlocksize(2097152);

            //设置版本
            fans.setMinVersions(1);
            fans.setMaxVersions(1);

            hTableDescriptor.addFamily(fans);

            admin.createTable(hTableDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 建立微博接收信息表
     * rowkey：用户ID
     * 列族：info
     * 列限定名：我关注的人的ID
     * 值：内容表的Rowkey
     * 版本：1000
     */
    public void createTableReceiveContentEmail() {

        Admin admin = null;
        try {
            admin = connection.getAdmin();
            //建表
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_RECEIVE_CONTENT_EMAIL));

            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("info");

            //快缓存开启
            hColumnDescriptor.setBlockCacheEnabled(true);
            //块缓存大小
            hColumnDescriptor.setBlocksize(2097152);

            //设置版本
            hColumnDescriptor.setMinVersions(1000);
            hColumnDescriptor.setMaxVersions(1000);


            hTableDescriptor.addFamily(hColumnDescriptor);

            admin.createTable(hTableDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 发送微博内容
     * <p>
     * 1. 要把发送的内容写进内容表
     * 2. 要把我们的发送的消息推给粉丝
     *
     * @param uid     谁发送的
     * @param content 发送了什么东西
     */
    public void publishContent(String uid, String content) {
        Table tableContent = null;
        Table tableRelations = null;
        Table tableReceiveContentEmail = null;
        try {
            tableContent = connection.getTable(TableName.valueOf(TABLE_CONTENT));
            tableRelations = connection.getTable(TableName.valueOf(TABLE_RELATIONS));
            tableReceiveContentEmail = connection.getTable(TableName.valueOf(TABLE_RECEIVE_CONTENT_EMAIL));

            //1. 发送内容
            //获取时间戳并拼接rowkey
            long timestamp = System.currentTimeMillis();
            String rowkey = uid + "_" + timestamp;

            Put put = new Put(rowkey.getBytes());

            //添加要发送的微博内容
            put.add(new KeyValue(
                    rowkey.getBytes(),
                    "info".getBytes(),
                    "content".getBytes(),
                    content.getBytes()
            ));

            tableContent.put(put);

            //2. 推送给粉丝
            //查粉丝
            Get get = new Get(uid.getBytes());
            get.addFamily("fans".getBytes());
            Result result = tableRelations.get(get);

            //给粉丝推送消息
            for (Cell cell : result.rawCells()) {
                byte[] fan = CellUtil.cloneQualifier(cell);

                Put fanPut = new Put(fan);

                fanPut.add(new KeyValue(
                        fan,
                        "info".getBytes(),
                        uid.getBytes(),
                        timestamp,
                        rowkey.getBytes()
                ));

                tableReceiveContentEmail.put(fanPut);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tableContent != null) {
                try {
                    tableContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableRelations != null) {
                try {
                    tableRelations.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableReceiveContentEmail != null) {
                try {
                    tableReceiveContentEmail.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 添加关注
     * <p>
     * 1. 关系表给我添加我要关注的人
     * 2. 给被我关注的人添加粉丝（我）
     * 3. 给我的收件箱添加消息
     *
     * @param uid     添加关注的人
     * @param attends 新关注了哪些人
     */
    public void addAttends(String uid, String... attends) {
        Table tableContent = null;
        Table tableRelations = null;
        Table tableReceiveContentEmail = null;
        try {
            tableContent = connection.getTable(TableName.valueOf(TABLE_CONTENT));
            tableRelations = connection.getTable(TableName.valueOf(TABLE_RELATIONS));
            tableReceiveContentEmail = connection.getTable(TableName.valueOf(TABLE_RECEIVE_CONTENT_EMAIL));

            //1. 给我添加关注
            Put attendPut = new Put(uid.getBytes());
            List<Put> fanPuts = new ArrayList<Put>();

            //添加邮件的Put
            Put emailPut = new Put(uid.getBytes());

            //用它来扫描我们的内容表
            Scan scan = new Scan();
            for (String attend : attends) {
                //添加关注
                attendPut.add(new KeyValue(
                        uid.getBytes(),
                        "attends".getBytes(),
                        attend.getBytes(),
                        attend.getBytes()
                ));

                //添加粉丝
                Put fanPut = new Put(attend.getBytes());
                //添加
                fanPut.add(new KeyValue(
                        attend.getBytes(),
                        "fans".getBytes(),
                        uid.getBytes(),
                        uid.getBytes()
                ));
                fanPuts.add(fanPut);

                //2. 将这个rowkey添加到我的收件箱
                //  a. 从内容表中查出我关注的人发送的消息（rowkey）
                RowFilter filter = new RowFilter(                   //新建过滤器找到我们需要的行
                        CompareFilter.CompareOp.EQUAL,              //过滤的逻辑符号
                        new SubstringComparator(attend + "_"));     //过滤的逻辑
                scan.setFilter(filter);

                //results包含了attend这个人发送的所有的消息
                ResultScanner results = tableContent.getScanner(scan);
                for (Result result : results) {
                    //获取这一行内容的rowkey
                    byte[] rowkey = result.getRow();

                    //row就是uid_timestamp
                    String row = Bytes.toString(rowkey);
                    long timeStamp = Long.parseLong(row.substring(row.lastIndexOf('_') + 1));

                    //每一个rowkey都要添加进emailPut
                    emailPut.add(new KeyValue(
                            uid.getBytes(),
                            "info".getBytes(),
                            attend.getBytes(),
                            timeStamp,
                            rowkey
                    ));

                }


            }

            tableRelations.put(attendPut);
            tableRelations.put(fanPuts);

            tableReceiveContentEmail.put(emailPut);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tableContent != null) {
                try {
                    tableContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableRelations != null) {
                try {
                    tableRelations.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableReceiveContentEmail != null) {
                try {
                    tableReceiveContentEmail.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    /**
     * 取消关注
     * <p>
     * 1. 我取消关注的人
     * 2. 这些人移除我这个粉丝
     * 3. 从我收件箱里删除消息
     *
     * @param uid
     * @param attends
     */
    public void removeAttends(String uid, String... attends) {

        Table tableContent = null;
        Table tableRelations = null;
        Table tableReceiveContentEmail = null;
        try {
            tableContent = connection.getTable(TableName.valueOf(TABLE_CONTENT));
            tableRelations = connection.getTable(TableName.valueOf(TABLE_RELATIONS));
            tableReceiveContentEmail = connection.getTable(TableName.valueOf(TABLE_RECEIVE_CONTENT_EMAIL));

            //1. 我取消关注
            Delete attendDelete = new Delete(uid.getBytes());
            List<Delete> fanDeletes = new ArrayList<Delete>();

            //2. 收件箱删除对象
            Delete emailDelete = new Delete(uid.getBytes());

            for (String attend : attends) {

                //添加所有要取关的人
                attendDelete.addColumns(
                        "attends".getBytes(),
                        attend.getBytes()
                );

                //给fanDeletes集合添加元素
                //2. 给每个attend移除粉丝
                Delete fanDelete = new Delete(attend.getBytes());
                fanDelete.addColumns(
                        "fans".getBytes(),
                        uid.getBytes()
                );
                fanDeletes.add(fanDelete);

                //3. 从我的收件箱把attend的消息移除
                emailDelete.addColumns(
                        "info".getBytes(),
                        attend.getBytes(),
                        System.currentTimeMillis()
                );


            }

            tableRelations.delete(attendDelete);
            tableRelations.delete(fanDeletes);

            tableReceiveContentEmail.delete(emailDelete);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tableContent != null) {
                try {
                    tableContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableRelations != null) {
                try {
                    tableRelations.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableReceiveContentEmail != null) {
                try {
                    tableReceiveContentEmail.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 获取我关注的所有信息，并包装成Message List
     * 1. 获取我的收件箱的内容
     * 2. 拿到Rowkey，获取信息内容，包装成Message
     * 3. 获取我自己的消息
     * @param uid 要拉取信息的人
     * @return 所有信息的集合
     */
    public List<Message> getAttendContent(String uid) {
        Table tableContent = null;
        Table tableRelations = null;
        Table tableReceiveContentEmail = null;
        try {
            tableContent = connection.getTable(TableName.valueOf(TABLE_CONTENT));
            tableRelations = connection.getTable(TableName.valueOf(TABLE_RELATIONS));
            tableReceiveContentEmail = connection.getTable(TableName.valueOf(TABLE_RECEIVE_CONTENT_EMAIL));

            List<Message> messages = new ArrayList<Message>();

            //0. 获取我自己发的消息
            Scan scan = new Scan();
            scan.setFilter(new RowFilter(
                    CompareFilter.CompareOp.EQUAL,
                    new SubstringComparator(uid + "_")
            ));

            ResultScanner results = tableContent.getScanner(scan);
            for (Result result : results) {
                Message message = new Message();
                byte[] rowkey = result.getRow();
                String row = Bytes.toString(rowkey);
                message.setUid(row.substring(0, row.lastIndexOf('_')));
                message.setTimestamp(row.substring(row.lastIndexOf('_') + 1));

                for (Cell cell : result.rawCells()) {
                    if ("content".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        message.setContent(Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }

                messages.add(message);

            }


            //1. 获取我的收件箱的所有内容
            Get emailGet = new Get(uid.getBytes());
            emailGet.setMaxVersions(10);
            Result result = tableReceiveContentEmail.get(emailGet);

            //2. 遍历Result，拿出所有的Rowkey，查找到对应的消息，包装成Message
            for (Cell cell : result.rawCells()) {
                byte[] rowkey = CellUtil.cloneValue(cell);

                //查rowkey对应的消息，包装Message
                Message message = new Message();

                Get contentGet = new Get(rowkey);
                Result contents = tableContent.get(contentGet);
                for (Cell cell1 : contents.rawCells()) {
                    if ("content".equals(Bytes.toString(CellUtil.cloneQualifier(cell1)))) {
                        message.setContent(Bytes.toString(CellUtil.cloneValue(cell1)));
                    }
                }
                String row = Bytes.toString(rowkey);
                message.setUid(row.substring(0, row.lastIndexOf('_')));
                message.setTimestamp(row.substring(row.lastIndexOf('_') + 1));

                messages.add(message);

            }

            return messages;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tableContent != null) {
                try {
                    tableContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableRelations != null) {
                try {
                    tableRelations.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tableReceiveContentEmail != null) {
                try {
                    tableReceiveContentEmail.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //关闭连接
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
