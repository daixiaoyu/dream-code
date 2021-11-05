package com.dai.flink.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

/**
 * @ClassName KafkaSavaParquet
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/10/17 3:54 下午
 * @Version 1.0
 **/
public class KafkaSavaParquet {
    public static void main(String[] args) {
        EnvironmentSettings environmentSettings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();
        TableEnvironment tableEnvironment = TableEnvironment.create(environmentSettings);

        tableEnvironment.executeSql(" CREATE TABLE kafka_log_ali\n" +
                "(\n" +
                "    `___dp_event___` BOOLEAN,\n" +
                "    `_id`            STRING,\n" +
                "    `_source`        STRING,\n" +
                "    `_event`         STRING,\n" +
                "    `_ts`            BIGINT,\n" +
                "    `account_id`     STRING,\n" +
                "    `user_id`        STRING,\n" +
                "    `union_id`       STRING,\n" +
                "    `app_type`       STRING,\n" +
                "    `material_ids`   STRING,\n" +
                "    `material_type`  STRING,\n" +
                "    `task_id`        STRING,\n" +
                "    `share_type`     STRING,\n" +
                "    `stay_seconds`   BIGINT\n" +
                ") WITH (\n" +
                "      'connector' = 'kafka',\n" +
                "      'topic' = 'ali-event',\n" +
                "      'properties.bootstrap.servers' = 'localhost:9092',\n" +
                "      'properties.group.id' = 'daixinyutest',\n" +
                "      'scan.startup.mode' = 'earliest-offset',\n" +
                "      'format' = 'json'\n" +
                "      )");

        tableEnvironment.executeSql("CREATE TABLE fs_log_ali\n" +
                "(\n" +
                "    `___dp_event___` BOOLEAN,\n" +
                "    `_id`            STRING,\n" +
                "    `_source`        STRING,\n" +
                "    `_event`         STRING,\n" +
                "    `_ts`            BIGINT,\n" +
                "    `account_id`     STRING,\n" +
                "    `user_id`        STRING,\n" +
                "    `union_id`       STRING,\n" +
                "    `app_type`       STRING,\n" +
                "    `material_ids`   STRING,\n" +
                "    `material_type`  STRING,\n" +
                "    `task_id`        STRING,\n" +
                "    `share_type`     STRING,\n" +
                "    `stay_seconds`   BIGINT,\n" +
                "    `dt`             STRING\n" +
                ") PARTITIONED BY (dt) WITH (\n" +
                "  'connector'='filesystem',\n" +
                "  'path'='hdfs://localhost:9000/tmp/test/ali',\n" +
                "  'format'='parquet',\n" +
                "  'sink.partition-commit.delay'='1 h',\n" +
                "  'sink.partition-commit.policy.kind'='success-file'\n" +
                ")");

        tableEnvironment.executeSql("INSERT INTO fs_log_ali\n" +
                "SELECT a.*,\n" +
                "       from_unixtime(_ts, 'yyyy-MM-dd')\n" +
                "FROM kafka_log_ali a");
    }
}
