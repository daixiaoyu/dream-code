package com.dai.iot.hudi;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @ClassName HudiTest
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/16 5:54 下午
 * @Version 1.0
 **/
public class HudiBatchTest {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inBatchMode()
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
                "  'table.type' = 'MERGE_ON_READ'\n" +
                ")");


        TableResult tableResult = tableEnvironment.executeSql("select * from t1");
        tableResult.print();

//        tableEnvironment.executeSql("INSERT INTO t1 VALUES\n" +
//                "  ('id1','Danny',23,TIMESTAMP '1970-01-01 00:00:01','par1'),\n" +
//                "  ('id2','Stephen',33,TIMESTAMP '1970-01-01 00:00:02','par1'),\n" +
//                "  ('id3','Julian',53,TIMESTAMP '1970-01-01 00:00:03','par2'),\n" +
//                "  ('id4','Fabian',31,TIMESTAMP '1970-01-01 00:00:04','par2'),\n" +
//                "  ('id5','Sophia',18,TIMESTAMP '1970-01-01 00:00:05','par3'),\n" +
//                "  ('id6','Emma',20,TIMESTAMP '1970-01-01 00:00:06','par3'),\n" +
//                "  ('id7','Bob',44,TIMESTAMP '1970-01-01 00:00:07','par4'),\n" +
//                "  ('id8','Han',56,TIMESTAMP '1970-01-01 00:00:08','par4') ");

        tableEnvironment.executeSql(" insert into t1 values ('id9','test',27,TIMESTAMP '1970-01-01 00:00:01','par5') ");


    }
}
