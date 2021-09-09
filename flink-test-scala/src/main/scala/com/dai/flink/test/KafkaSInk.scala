package com.dai.flink.test



import java.util.Properties

import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

object KafkaSInk {

  def main(args: Array[String]): Unit = {
    val stream: DataStream[String] = null

    val properties = new Properties
    properties.setProperty("bootstrap.servers", "localhost:9092")

//    val myProducer = new FlinkKafkaProducer[String](
//      "my-topic",                  // target topic
//      null,    // serialization schema
//      properties,                  // producer config
//      FlinkKafkaProducer.Semantic.EXACTLY_ONCE) // fault-tolerance
//
//    stream.addSink(myProducer)

  }

}
