package com.dai.flink.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @ClassName KafkaSendTest
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/27 12:26 上午
 * @Version 1.0
 **/
public class KafkaSendTest {
    public static void main(String[] args) {
        EnvironmentSettings environmentSettings = EnvironmentSettings.newInstance()
                .useBlinkPlanner()
                .inStreamingMode()
                .build();
        TableEnvironment environment = TableEnvironment.create(environmentSettings);

        environment.executeSql("CREATE TABLE kafka_order (\n" +
                "  id INT,\n" +
                "  amount INT,\n" +
                "  user_id STRING,\n" +
                "  vender_id STRING,\n" +
                "  create_time STRING\n" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'flink_order',\n" +
                "  'properties.bootstrap.servers' = 'localhost:9092',\n" +
                "  'properties.group.id' = 'testGroup',\n" +
                "  'scan.startup.mode' = 'earliest-offset',\n" +
                "  'format' = 'json'\n" +
                ")");

        //environment.executeSql("insert into kafka_order values(1,20,'1','1','2021-09-26 00:00:00')");

        TableResult tableResult = environment.executeSql("select * from kafka_order");

        tableResult.print();
    }
}
