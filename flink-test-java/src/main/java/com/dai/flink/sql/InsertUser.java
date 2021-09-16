package com.dai.flink.sql;

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
public class InsertUser {
    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();
        TableEnvironment tableEnvironment = TableEnvironment.create(settings);

        tableEnvironment.executeSql("CREATE TABLE flink_user (\n" +
                "  id DECIMAL(20, 0),\n" +
                "  name STRING,\n" +
                "  address STRING,\n" +
                "  age INT,\n" +
                "  status INT,\n" +
                "  PRIMARY KEY (id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "   'connector' = 'jdbc',\n" +
                "   'driver' = 'com.mysql.cj.jdbc.Driver',\n" +
                "   'url' = 'jdbc:mysql://localhost:3306/flink?characterEncoding=utf8',\n" +
                "   'table-name' = 'user',\n" +
                "   'username' = 'root',\n" +
                "   'password' = 'root'\n" +
                ")");

//      tableEnvironment.executeSql("insert into flink_user values(1,'代欣雨','麓山',27,1),(2,'韩佩伶','成华',25,1)");
        TableResult tableResult = tableEnvironment.executeSql("select age,count(1) as rs from flink_user group by age");
        tableResult.print();
    }
}
