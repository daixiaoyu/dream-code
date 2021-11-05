package com.dai.flink.sql.order;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @ClassName File2Mysql
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/30 7:09 下午
 * @Version 1.0
 **/
public class File2Mysql {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings.
                newInstance().
                useBlinkPlanner().
                inBatchMode().
                build();

        TableEnvironment tableEnv = TableEnvironment.create(settings);

        tableEnv.executeSql("CREATE TABLE flink_file_order (\n" +
                "  id INT,\n" +
                "  amount INT,\n" +
                "  user_id STRING,\n" +
                "  vender_id STRING,\n" +
                "  create_time TIMESTAMP(3),\n" +
                "  WATERMARK FOR create_time AS create_time - INTERVAL '0' SECOND\n" +
                ") WITH (\n" +
                "  'connector' = 'filesystem',  \n" +
                "  'path' = '/Users/gump/study/source/github/dream-code/flink-test-java/src/main/resources/data/orderSql.txt',  \n" +
                "  'format' = 'json'\n" +
                ")");

        tableEnv.executeSql("-- 创建Flink表\n" +
                "CREATE TABLE flink_mysql_order (\n" +
                "  id INT,\n" +
                "  amount INT,\n" +
                "  user_id STRING,\n" +
                "  vender_id STRING,\n" +
                "  create_time TIMESTAMP(3),\n" +
                "  PRIMARY KEY (id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "   'connector' = 'jdbc',\n" +
                "   'driver' = 'com.mysql.cj.jdbc.Driver',\n" +
                "   'url' = 'jdbc:mysql://localhost:3306/flink?characterEncoding=utf8',\n" +
                "   'table-name' = 'mysql_order',\n" +
                "   'username' = 'root',\n" +
                "   'password' = 'root',\n" +
                "   'sink.buffer-flush.interval' = '5',\n" +
                "   'sink.buffer-flush.max-rows' = '10',\n" +
                "   'sink.parallelism' = '1'\n" +
                ")");

        tableEnv.executeSql("insert into flink_mysql_order select * from flink_file_order");

        TableResult tableResult = tableEnv.executeSql("select * from flink_mysql_order");
        tableResult.print();

    }
}
