
# Flink Yarn Run
``` 
 启动Hadoop yarn
 
 start-all.sh
 
 mvn clean package
 
 java
 
 /Users/gump/dreamware/flink-1.13.0/bin/flink 
 run-application 
 -t yarn-application 
 /Users/gump/dream-code/flink-test-java/target/flink-test-java-0.1.jar
 
 scala
 
 /Users/gump/dreamware/flink-1.13.0/bin/flink 
 run-application 
 -t yarn-application 
 /Users/gump/dream-code/flink-test-scala/target/flink-test-scala-0.1.jar
 

```


# Spark Yarn Submit
```
spark-submit 
--master yarn 
/Users/gump/dream-code/spark-test-scala/target/spark-test-scala-1.0-SNAPSHOT.jar


```
