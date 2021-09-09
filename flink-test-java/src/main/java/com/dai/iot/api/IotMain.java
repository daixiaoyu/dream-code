package com.dai.iot.api;


import com.dai.iot.model.DeviceType;
import com.dai.iot.model.OilQualityData;
import com.dai.iot.model.OilQualityType;
import com.dai.iot.model.TemperatureData;
import com.dai.iot.util.DataUtil;
import com.dai.iot.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.client.JobExecutionException;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.TimeEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousEventTimeTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.time.Duration;
import java.util.*;


/**
 * 启动Hadoop yarn
 * <p>
 * start-all.shv
 * <p>
 * mvn clean package
 * <p>
 * /Users/gump/dreamware/flink-1.13.0/bin/flink run-application -t yarn-application /Users/gump/dream-code/flink-test-java/target/flink-test-java-0.1.jar
 */
public class IotMain {
    public static Logger logger = LoggerFactory.getLogger(IotMain.class);

    public static final String INPUT_TXT = "/Users/gump/dream-code/flink-test-java/src/main/resources/iot.txt1";

//    public static String INPUT_TXT = "iot.txt";

    public static void main(String[] args) {

        // create environment of  Flink
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        // Read the data from the TXT file
        // In production we read data from Kafka or Socket
        DataStreamSource<String> deviceData = env.readTextFile(INPUT_TXT);

        // Separate device data for parallel monitoring
        Tuple2<DataStream<Object>, DataStream<OilQualityData>> totalStream = splitDeviceData(deviceData);

        // Monitoring temperature DataStream
        Tuple2<DataStream<String>, DataStream<String>> temperatureResult = monitorTemperature(totalStream._1);

        // union the warning data
        DataStream<String> allWarningResult = temperatureResult._1.union(monitorOilQuality(totalStream._2));

        // sink the waring data
        allWarningResult.addSink(new RichSinkFunction<String>() {
            private int count = 0;

            @Override
            public void invoke(String value, Context context) throws Exception {
                if (count == 0) {
                    System.out.println("异常检测结果:");
                }
                count++;
                System.out.println(value);
            }
        }).setParallelism(1);

        // sink the report data
        temperatureResult._2().addSink(new RichSinkFunction<String>() {
            @Override
            public void invoke(String value, Context context) throws Exception {
                System.out.println("报表结果:");
                System.out.println(value);
            }
        }).setParallelism(1);

        try {
            env.execute("iotCheck");
        } catch (Exception e) {
            if (e instanceof JobExecutionException) {
                JobExecutionException jobExecutionException = (JobExecutionException) e;
                if (jobExecutionException.getCause() instanceof java.io.FileNotFoundException) {
                    logger.error("因为环境原因相对路径文件无法加载，请使用绝对路径替换INPUT_TXT属性: {}", INPUT_TXT);
                } else {
                    logger.error("任务运行异常: {}", e);
                }
            }
        }

    }


    public static Tuple2<DataStream<String>, DataStream<String>> monitorTemperature(DataStream<Object> temperatureDataDataStream) {

        OutputTag<String> reportTag = new OutputTag<>("reportTag", TypeInformation.of(String.class));

        WatermarkStrategy<Object> temperatureDataWatermarkStrategy = WatermarkStrategy.<Object>forBoundedOutOfOrderness(Duration.ofSeconds(20))
                .withTimestampAssigner((event, timestamp) -> {

                    if (event instanceof OilQualityData) {
                        OilQualityData oilQualityData = (OilQualityData) event;
                        return oilQualityData.getEventTime();
                    } else {
                        TemperatureData temperatureData = (TemperatureData) event;
                        return temperatureData.getEventTime();
                    }

                });

        SingleOutputStreamOperator<String> resultStream = temperatureDataDataStream.assignTimestampsAndWatermarks(temperatureDataWatermarkStrategy)
                .keyBy(object -> {
                    if (object instanceof OilQualityData) {
                        OilQualityData oilQualityData = (OilQualityData) object;
                        return DateUtil.getDt(oilQualityData.getCreateTime()) ;
                    } else {
                        TemperatureData temperatureData = (TemperatureData) object;
                        return DateUtil.getDt(temperatureData.getCreateTime());
                    }
                })
                .window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(16)))
                .trigger(ContinuousEventTimeTrigger.of(Time.seconds(1)))
                .evictor(TimeEvictor.of(Time.seconds(0), true))
                .process(new ProcessWindowFunction<Object, String, String, TimeWindow>() {
                    private ValueState<Float> temAverageStage;
                    private ValueState<Float> sumStage;
                    private ValueState<Long> countStage;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        temAverageStage = getRuntimeContext().getState(new ValueStateDescriptor<>("tem_average", Float.class));
                        sumStage = getRuntimeContext().getState(new ValueStateDescriptor<>("sum_tem", Float.class));
                        countStage = getRuntimeContext().getState(new ValueStateDescriptor<>("count_tem", Long.class));
                    }

                    @Override
                    public void process(String deviceId, Context context, Iterable<Object> dataSet, Collector<String> collector) throws Exception {

                        Iterator<Object> iterator = dataSet.iterator();

                        Deque<OilQualityData> oilQualityDataStack = new LinkedList<>();

                        while (iterator.hasNext()) {
                            Object data = iterator.next();
                            TemperatureData temperatureData;
                            if (data instanceof OilQualityData) {
                                OilQualityData oilQualityData = (OilQualityData) data;
                                oilQualityDataStack.push(oilQualityData);
                                // 油品数据只保存，不计算
                                continue;
                            } else {
                                temperatureData = (TemperatureData) data;
                            }

                            Float temAverage = temAverageStage.value();
                            Float nowTem = temperatureData.getValue();

                            if (null == temAverage) {
                                temAverageStage.update(0F);
                                sumStage.update(0F);
                                countStage.update(0L);
                            } else {
                                if ((nowTem - TemperatureData.WARNING_THAN_AVERAGE) > temAverage) {
                                    OilQualityData oilQualityData = oilQualityDataStack.pop();
                                    Float ceValue = oilQualityData.getDataMap().get(OilQualityType.CE.getCode());
                                    collector.collect(temperatureData.getSource() + "温度过高，最近一次含水量:" + ceValue);
                                }
                            }

                            Float sum = sumStage.value() + nowTem;
                            Long count = countStage.value() + 1;
                            Float average = DataUtil.divide(sum, count);
                            sumStage.update(sum);
                            countStage.update(count);
                            temAverageStage.update(average);
                        }
                        // 输出报表
                        context.output(reportTag, "温度：" + DateUtil.getDt(context.window().getStart()) + " " + temAverageStage.value());
                    }

                    @Override
                    public void clear(Context context) throws Exception {
                        super.clear(context);
                        temAverageStage.clear();
                        sumStage.clear();
                        countStage.clear();
                    }
                });


        DataStream<String> report = resultStream.getSideOutput(reportTag);

        return new Tuple2(resultStream, report);
    }

    /**
     * Separate temperature and oil product data
     *
     * @param deviceData
     * @return
     */
    public static Tuple2<DataStream<Object>, DataStream<OilQualityData>> splitDeviceData(DataStream<String> deviceData) {

        OutputTag<OilQualityData> oilTag = new OutputTag<>("oil", TypeInformation.of(OilQualityData.class));

        SingleOutputStreamOperator<Object> temperatureStream = deviceData.process(new ProcessFunction<String, Object>() {
            @Override
            public void processElement(String dataLine, Context context, Collector<Object> collector) throws Exception {
                if (StringUtils.isBlank(dataLine)) {
                    return;
                }
                if (dataLine.startsWith(DeviceType.TEMPERATURE.getCode())) {
                    Optional<TemperatureData> temperatureData = DataUtil.parseToTem(dataLine);
                    if (temperatureData.isPresent()) {
                        collector.collect(temperatureData.get());
                    }

                } else if (dataLine.startsWith(DeviceType.OIL_QUALITY.getCode())) {
                    Optional<OilQualityData> oilQualityData = DataUtil.parseToOil(dataLine);
                    if (oilQualityData.isPresent()) {
                        context.output(oilTag, oilQualityData.get());
                        collector.collect(oilQualityData.get());
                    }
                }
            }
        });

        return new Tuple2<>(temperatureStream, temperatureStream.getSideOutput(oilTag));
    }


    /**
     * Monitor oil production by FLINK CEP
     *
     * @param oilQualityDataDataStream
     * @return
     */
    public static DataStream<String> monitorOilQuality(DataStream<OilQualityData> oilQualityDataDataStream) {
        WatermarkStrategy<OilQualityData> oilQualityDataWatermarkStrategy = WatermarkStrategy.<OilQualityData>forBoundedOutOfOrderness(Duration.ofSeconds(20))
                .withTimestampAssigner((event, timestamp) -> event.getEventTime());
        SingleOutputStreamOperator<OilQualityData> oilWaterStream =
                oilQualityDataDataStream.assignTimestampsAndWatermarks(oilQualityDataWatermarkStrategy);

        // the pattern
        Pattern pattern = Pattern.<OilQualityData>begin("first").where(new checkIfMoreThanPre())
                .next("second")
                .where(new checkIfMoreThanPre());

        SingleOutputStreamOperator<Map<String, List<OilQualityData>>> select = CEP.pattern(oilWaterStream, pattern)
                .select(new PatternSelectFunction<OilQualityData, Map<String, List<OilQualityData>>>() {
                    @Override
                    public Map<String, List<OilQualityData>> select(Map<String, List<OilQualityData>> map) throws Exception {
                        List<OilQualityData> first = map.get("first");
                        List<OilQualityData> second = map.get("second");
                        if (first.size() > 0) {
                            first.get(0).appendWarning("第一次");
                        }
                        if (second.size() > 0) {
                            second.get(0).appendWarning("第二次");
                        }
                        return map;
                    }
                });

        SingleOutputStreamOperator<String> result = select.flatMap(new RichFlatMapFunction<Map<String, List<OilQualityData>>, String>() {
            @Override
            public void flatMap(Map<String, List<OilQualityData>> data, Collector<String> collector) throws Exception {
                data.forEach((k, v) -> collector.collect(v.get(0).getWarningMsg()));
            }
        });

        return result;
    }

    static class checkIfMoreThanPre extends IterativeCondition<OilQualityData> {
        private OilQualityData pre;

        @Override
        public boolean filter(OilQualityData oilQualityData, Context<OilQualityData> context) {
            if (null == pre) {
                pre = oilQualityData;
                return false;
            }
            return DataUtil.isWarning(oilQualityData, pre);
        }
    }


}
