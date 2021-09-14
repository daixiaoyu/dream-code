package com.dai.iot.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @ClassName SqlDemo1
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/3 5:22 下午
 * @Version 1.0
 **/
public class OrderSelect {
    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();

        TableEnvironment tableEnvironment = TableEnvironment.create(settings);

        tableEnvironment.executeSql(
                "CREATE TABLE flink_order (\n" +
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
                        ")"
        );

        //TableResult tableResult = tableEnvironment.executeSql("select user_id,count(1) as rs from flink_order group by user_id");

        TableResult tableResult = tableEnvironment.executeSql("SELECT \n" +
                "       window_start, \n" +
                "       window_end,\n" +
                "       vender_id, \n" +
                "       SUM(amount) as amount, \n" +
                "       COUNT(*) as cnt\n" +
                " FROM TABLE(\n" +
                "        TUMBLE(TABLE flink_order, DESCRIPTOR(create_time), INTERVAL '7' DAYS))\n" +
                " GROUP BY window_start, window_end, vender_id");

        tableResult.print();
    }
}
