package com.dai.iot.sql;

import com.google.common.base.Preconditions;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.catalog.hive.HiveCatalog;

import java.net.URL;

/**
 * @ClassName HiveTest
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/13 5:09 下午
 * @Version 1.0
 **/
public class HiveCatalogTest {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);

        URL location = Preconditions.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.toString());

        String catalog_name = "hive_catalog";

        String database = "flink";

        String hiveConfDir = "/Users/gump/dreamware/apache-hive-3.1.2-bin/conf";

        HiveCatalog hive = new HiveCatalog(catalog_name, database, hiveConfDir);
        tableEnv.registerCatalog(catalog_name, hive);

        tableEnv.useCatalog(catalog_name);


        tableEnv.executeSql(
                "CREATE TABLE flink_order (\n" +
                        "  id INT,\n" +
                        "  amount INT,\n" +
                        "  user_id STRING,\n" +
                        "  vender_id STRING,\n" +
                        "  create_time TIMESTAMP(3),\n" +
                        "  WATERMARK FOR create_time AS create_time - INTERVAL '0' SECOND\n" +
                        ") WITH (\n" +
                        "  'connector' = 'filesystem',  \n" +
                        "  'path' = '/Users/gump/study/source/github/dream-code/flink-test-java/src/main/resources/orderSql.txt',  \n" +
                        "  'format' = 'json'\n" +
                        ")"
        );

        TableResult tableResult = tableEnv.executeSql("show tables");

        tableResult.print();
    }
}
