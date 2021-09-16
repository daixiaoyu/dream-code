package com.dai.flink.hudi;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @ClassName HudiStreamTest
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/16 7:36 下午
 * @Version 1.0
 **/
public class HudiStreamTest {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();

        TableEnvironment tableEnvironment = TableEnvironment.create(settings);

        tableEnvironment.executeSql("CREATE TABLE t1(\n" +
                "  uuid VARCHAR(20),\n" +
                "  name VARCHAR(10),\n" +
                "  age INT,\n" +
                "  ts TIMESTAMP(3),\n" +
                "  `partition` VARCHAR(20)\n" +
                ")\n" +
                "PARTITIONED BY (`partition`)\n" +
                "WITH (\n" +
                "  'connector' = 'hudi',\n" +
                "  'path' = 'hdfs://localhost:9000/hudi/t1',\n" +
                "  'table.type' = 'MERGE_ON_READ',\n" +
                "  'read.streaming.enabled' = 'true',  \n" +
                "  'read.streaming.start-commit' = '20210401134557' ,\n" +
                "  'read.streaming.check-interval' = '4'\n" +
                ")");


        TableResult tableResult = tableEnvironment.executeSql("select * from t1");
        tableResult.print();
    }
}
