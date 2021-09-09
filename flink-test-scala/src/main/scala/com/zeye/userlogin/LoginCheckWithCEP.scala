package com.zeye.userlogin

import  java.util

import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time



object LoginCheckWithCEP {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    //步骤一：读取事件数据
    val loginEventStream = env.readTextFile("/Users/gump/my-code/flink-test/src/main/resources/data4.csv")
      .map( data => {
        val dataArray = data.split(",")
        LoginEvent( dataArray(0).trim.toLong,
          dataArray(1).trim, dataArray(2).trim, dataArray(3).trim.toLong )
      } ).assignTimestampsAndWatermarks(new LoginCheckEventTimeExtractor())
      .keyBy(_.userId)

    //步骤二：定义匹配模式

    val loginFailPattern = Pattern.begin[LoginEvent]("begin").where(_.eventType == "fail")
      .next("next").where(_.eventType == "fail")
      .within(Time.seconds(3))

    //步骤三：在事件流上应用模式，得到一个pattern stream

    val patternStream = CEP.pattern(loginEventStream, loginFailPattern)

    //步骤四从pattern stream上应用select function，检出匹配事件序列

    val loginFailDataStream = patternStream.select( new LoginFailMatch() )

    loginFailDataStream.print()

    env.execute("login fail check ......")

  }

}

class LoginFailMatch() extends PatternSelectFunction[LoginEvent, Warning]{
  override def select(map: util.Map[String, util.List[LoginEvent]]): Warning = {
    // 从map中按照名称取出对应的事件
    val firstFail = map.get("begin").iterator().next()
    val lastFail = map.get("next").iterator().next()
    Warning( firstFail.userId, firstFail.eventTime, lastFail.eventTime, "login fail!" )
  }
}


