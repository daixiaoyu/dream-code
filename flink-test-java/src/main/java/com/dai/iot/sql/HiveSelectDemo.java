package com.dai.iot.sql;

import com.google.common.base.Preconditions;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.catalog.hive.HiveCatalog;

import java.net.URL;

/**
 * @ClassName HiveSelectDemo
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/13 8:33 下午
 * @Version 1.0
 **/
public class HiveSelectDemo {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.
                newInstance().
                useBlinkPlanner().
                inBatchMode().
                build();

        TableEnvironment tableEnv = TableEnvironment.create(settings);

        URL location = Preconditions.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.toString());

        String catalog_name = "hive_catalog";

        String database = "default";

        String hiveConfDir = "/Users/gump/dreamware/apache-hive-3.1.2-bin/conf";

        HiveCatalog hive = new HiveCatalog(catalog_name, database, hiveConfDir);

        tableEnv.registerCatalog(catalog_name, hive);

        tableEnv.useCatalog(catalog_name);


        TableResult tableResult = tableEnv.executeSql("insert overwrite flink.course_all select course,count(1) as rs from exercise_course group by course");

        tableResult.print();

    }
}
