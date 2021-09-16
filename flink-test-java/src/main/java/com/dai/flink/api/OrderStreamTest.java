package com.dai.flink.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dai.flink.util.DateUtil;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.TimeEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousEventTimeTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.Iterator;

/**
 * @ClassName StreamTest
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/9/4 2:39 下午
 * @Version 1.0
 **/
public class OrderStreamTest {

    public static final String AMOUNT = "amount";
    public static final String CREATE_TIME = "create_time";
    public static final String EVEN_TIME = "event_time";
    public static final String VENDER_ID = "vender_id";

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(1);

//      DataStreamSource<String> source = environment.socketTextStream("localhost", 9999);

        DataStreamSource<String> source = environment.readTextFile("/Users/gump/dream-code/flink-test-java/src/main/resources/order.txt");

        // 设置 watermark
        WatermarkStrategy<JSONObject> jsonObjectWatermarkStrategy = WatermarkStrategy.<JSONObject>forBoundedOutOfOrderness(Duration.ofSeconds(0))
                .withTimestampAssigner((event, timestamp) -> event.getLong(CREATE_TIME));

        source.map(orderString -> {
            JSONObject order = JSON.parseObject(orderString);
            order.put(EVEN_TIME, DateUtil.getTimeLong(order.getString(CREATE_TIME)));
            return order;
        })
                .assignTimestampsAndWatermarks(jsonObjectWatermarkStrategy)
                .keyBy(s -> s.getString(VENDER_ID))
                .window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(16)))
                .trigger(ContinuousEventTimeTrigger.of(Time.seconds(5)))
                .evictor(TimeEvictor.of(Time.seconds(0), true))
                .process(new ProcessWindowFunction<JSONObject, JSONObject, String, TimeWindow>() {

                    MapState<String, JSONObject> sumState;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        MapStateDescriptor<String, JSONObject> sum = new MapStateDescriptor<>("sum", String.class, JSONObject.class);
                        sumState = getRuntimeContext().getMapState(sum);
                    }

                    @Override
                    public void process(String key, Context context, Iterable<JSONObject> elements, Collector<JSONObject> out) throws Exception {
                        Iterator<JSONObject> iterator = elements.iterator();
                        // 遍历窗口数据，获取唯一 word
                        while (iterator.hasNext()) {
                            JSONObject order = iterator.next();
                            String venderId = order.getString(VENDER_ID);
                            if (sumState.contains(venderId)) {
                                JSONObject jsonObject = sumState.get(venderId);
                                jsonObject.put("cnt", jsonObject.getLong("cnt") + 1);
                                sumState.put(venderId, jsonObject);
                            } else {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("cnt", 1);
                                jsonObject.put("key", venderId);
                                sumState.put(venderId, jsonObject);
                            }
                        }
                        Iterable<JSONObject> values = sumState.values();
                        values.forEach(jsonObject -> out.collect(jsonObject));
                    }

                })
                .addSink(new PrintSinkFunction<>()).setParallelism(1).name("daixinyu");

        environment.execute();
    }

}
