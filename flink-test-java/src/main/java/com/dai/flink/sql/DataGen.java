package com.dai.flink.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @ClassName DataGen
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/10/1 11:28 上午
 * @Version 1.0
 **/
public class DataGen {

    public static final String TABLE = "CREATE TABLE gen_order (\n" +
            " id INT,\n" +
            " amount INT,\n" +
            " vender_id INT,\n" +
            " ts AS localtimestamp,\n" +
            " WATERMARK FOR ts AS ts\n" +
            ") WITH (\n" +
            " 'connector' = 'datagen',\n" +
            " 'rows-per-second'='100',\n" +
            "  -- id\n" +
            " 'fields.id.kind'='sequence',\n" +
            " 'fields.id.start'='1',\n" +
            " 'fields.id.end'='100000000',\n" +
            "  -- amount\n" +
            " 'fields.amount.kind'='random',\n" +
            " 'fields.amount.min'='1',\n" +
            " 'fields.amount.max'='1',\n" +
            " \n" +
            "  -- vender_id\n" +
            " 'fields.vender_id.kind'='random',\n" +
            " 'fields.vender_id.min'='1',\n" +
            " 'fields.vender_id.max'='1'\n" +
            ")";
    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();
        TableEnvironment tableEnvironment = TableEnvironment.create(settings);

        tableEnvironment.executeSql(TABLE);

        TableResult tableResult = tableEnvironment.executeSql("select vender_id,sum(amount),max(localtimestamp) from gen_order group by vender_id");

        tableResult.print();

    }
}
