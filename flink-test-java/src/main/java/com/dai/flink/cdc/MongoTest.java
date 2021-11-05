package com.dai.flink.cdc;

import com.dai.flink.util.FlinkStreamSql;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @ClassName MongoTest
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/27 6:50 下午
 * @Version 1.0
 **/
public class MongoTest {

    private static String TABLE = "-- register a MongoDB table 'products' in Flink SQL\n" +
            "CREATE TABLE mongo_order(\n" +
            "  _id STRING, \n" +
            "  amount INT, \n" +
            "  user_id STRING,\n" +
            "  vender_id STRING,\n" +
            "  create_time STRING,\n" +
            "  -- WATERMARK FOR create_time AS create_time - INTERVAL '0' SECOND,\n" +
            "  PRIMARY KEY(_id) NOT ENFORCED\n" +
            ") WITH (\n" +
            "  'connector' = 'mongodb-cdc',\n" +
            "  'hosts' = 'localhost:27017,localhost:27018,localhost:27019',\n" +
            "  'username' = 'flinkuser',\n" +
            "  'password' = 'root',\n" +
            "  'database' = 'flink',\n" +
            "  'collection' = 'order'\n" +
            ")";
    public static void main(String[] args) {

        TableEnvironment tableEnvironment = TableEnvironment.create(EnvironmentSettings.newInstance()
                .useBlinkPlanner()
                 .inStreamingMode()
                .build()
        );

        // 创建 cdc 的 catalog 元数据
        // 字段对应 mongo 业务库数据表
        tableEnvironment.executeSql(TABLE);

        // 基于Flink SQL 的一个 简单的统计
        // 我们 可以 把CDC表直接写入 hudi  iceberg 等等 构建元数据统一的 批流一体数据湖
        TableResult tableResult = tableEnvironment.executeSql("select vender_id,sum(amount) as total_amount from mongo_order group by vender_id");

        // 打印到 控制台
        tableResult.print();
    }

}
