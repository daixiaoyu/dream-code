package com.dai.flink.util;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

/**
 * @ClassName FlinkSqlBase
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/27 6:51 下午
 * @Version 1.0
 **/
public class FlinkStreamSql {

    private static EnvironmentSettings settings = EnvironmentSettings
            .newInstance()
            .inStreamingMode()
            .useBlinkPlanner()
            .build();

    private static TableEnvironment tableEnvironment = TableEnvironment.create(settings);

    public static TableEnvironment getEnv() {
        return tableEnvironment;
    }
}
