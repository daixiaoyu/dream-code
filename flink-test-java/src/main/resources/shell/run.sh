flink run-application -t yarn-application  \
-c com.dai.flink.sql.OrderSelect  \
/Users/gump/study/source/github/dream-code/flink-test-java/target/flink-java.jar


flink run -t yarn-per-job  \
-c com.dai.flink.sql.OrderSelect  \
/Users/gump/study/source/github/dream-code/flink-test-java/target/flink-java.jar


flink run -t yarn-per-job  \
-c com.dai.flink.sql.HiveSelectDemo  \
/Users/gump/study/source/github/dream-code/flink-test-java/target/flink-java.jar