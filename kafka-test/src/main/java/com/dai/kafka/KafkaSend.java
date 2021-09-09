package com.dai.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaSend {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        Map<String, Long> map = new HashMap<>();

        for (long i = 0; i <1 ; i++) {
            map.put("itemId", i);
            map.put("price", i+1);
            producer.send(new ProducerRecord<String, String>("flink-catalog-v1", null, JSON.toJSONString(map)));
            producer.flush();
            Thread.sleep(1000L);
        }

        producer.close();
    }
}