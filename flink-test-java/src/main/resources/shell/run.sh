flink run-application -t yarn-application  \
-c com.dai.iot.sql.OrderSelect  \
/Users/gump/study/source/github/dream-code/flink-test-java/target/flink-java.jar


flink run -t yarn-per-job  \
-c com.dai.iot.sql.OrderSelect  \
/Users/gump/study/source/github/dream-code/flink-test-java/target/flink-java.jar


flink run -t yarn-per-job  \
-c com.dai.iot.sql.HiveSelectDemo  \
/Users/gump/study/source/github/dream-code/flink-test-java/target/flink-java.jar