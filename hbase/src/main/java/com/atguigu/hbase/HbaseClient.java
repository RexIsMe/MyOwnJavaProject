package com.atguigu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HbaseClient {

    private Connection connection;

    @Before
    public void before() throws IOException {
        //获取配置
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        
        //获取Connection
        connection = ConnectionFactory.createConnection(configuration);
    }

    @After
    public void after() throws IOException {
        //关闭
        connection.close();
    }

    /**
     * 建表
     */
    @Test
    public void createTable() throws IOException {
        //用Connection
        //找Master
        Admin admin = connection.getAdmin();

        //建表
        TableName name = TableName.valueOf("student");

        //建表
        if (!admin.tableExists(name)) {
            //建立表的描述器对象
            HTableDescriptor descriptor = new HTableDescriptor(name);
            //列族描述对象
            HColumnDescriptor column = new HColumnDescriptor("info");
            descriptor.addFamily(column);
            admin.createTable(descriptor);
        }
        admin.close();

    }

    /**
     * 删表
     *
     * @throws IOException
     */
    @Test
    public void deleteTable() throws IOException {
        //找Master
        Admin admin = connection.getAdmin();

        //删表
        TableName name = TableName.valueOf("student");

        if (admin.tableExists(name)) {
            //表删除之前首先要Disable
            admin.disableTable(name);
            admin.deleteTable(name);
        } else {
            System.out.println("表不存在");
        }

        admin.close();

    }

    /**
     * 插入数据
     */
    @Test
    public void addCell() throws IOException {

        //获取表
        Table student = connection.getTable(TableName.valueOf("student"));

//        //描述插入动作的对象
//        Put put = new Put("1001".getBytes());
//
//        put.add(new KeyValue("1001".getBytes(), //rowkey
//                "info".getBytes(),  //列族
//                "name".getBytes(),  //列限定名
//                "zhangsan".getBytes())); //值
//
//        put.add(new KeyValue("1001".getBytes(), //rowkey
//                "info".getBytes(),  //列族
//                "sex".getBytes(),  //列限定名
//                "male".getBytes())); //值
//
//        //往表里插入数据
//        student.put(put);

        addData(student, "1001", "info", "name", "zhangsan");
        addData(student, "1002", "info", "name", "lisi");
        addData(student, "1003", "info", "name", "wangwu");
        addData(student, "1001", "info", "age", "28");
        addData(student, "1002", "info", "age", "38");
        addData(student, "1003", "info", "age", "68");


        //关闭表
        student.close();

    }

    public void addData(Table table, String rowkey, String cf, String cq, String value) throws IOException {
        Put put = new Put(rowkey.getBytes());
        put.add(new KeyValue(
                rowkey.getBytes(),
                cf.getBytes(),
                cq.getBytes(),
                value.getBytes()
        ));
        table.put(put);
    }

    /**
     * 删除数据
     */
    @Test
    public void deleteCell() throws IOException {
        Table student = connection.getTable(TableName.valueOf("student"));

        //删除
        //描述删除动作的对象
        Delete delete = new Delete("1001".getBytes());

        //删除的时候，如果不指定列族和列限定名，可以一次删除一行
        delete.addColumns("info".getBytes(), "name".getBytes());

        student.delete(delete);

        student.close();
    }

    /**
     * 查数据
     */
    @Test
    public void getCell() throws IOException {
        Table student = connection.getTable(TableName.valueOf("student"));
        //查数据
        Get get = new Get("1001".getBytes());

//        get.addColumn("info".getBytes(), "sex".getBytes());

        get.addFamily("info".getBytes());

        Result result = student.get(get);

        Cell[] cells = result.rawCells();

        System.out.println("rowkey\tcf\tcq\tvalue");
        for (Cell cell : cells) {
            String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
            String cf = Bytes.toString(CellUtil.cloneFamily(cell));
            String cq = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            System.out.println(rowkey + "\t" + cf + "\t" + cq + "\t" + value);
        }


        student.close();
    }

    /**
     * 查数据二
     */
    @Test
    public void scan() throws IOException {
        Table student = connection.getTable(TableName.valueOf("student"));

        //描述Scan动作的对象
        Scan scan = new Scan("1001".getBytes(), "1003".getBytes());

        //获取Scanner
        ResultScanner scanner = student.getScanner(scan);

        //遍历结果
        for (Result result : scanner) {
            System.out.println("rowkey\tcf\tcq\tvalue");
            for (Cell cell : result.rawCells()) {
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String cq = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(rowkey + "\t" + cf + "\t" + cq + "\t" + value);
            }
        }

        student.close();
    }

    /**
     * 删除多行数据
     */
    @Test
    public void deleteMultipleRow() throws IOException {
        Table student = connection.getTable(TableName.valueOf("student"));

        deleteMulti(student, "1002", "1003");

        student.close();
    }

    private void deleteMulti(Table student, String ... rows) throws IOException {
        //建立delete集合
        List<Delete> deletes = new ArrayList<Delete>();

        //将要删除的行添加进集合
        for (String row : rows) {
            deletes.add(new Delete(row.getBytes()));
        }

        //删除
        student.delete(deletes);
    }
}
