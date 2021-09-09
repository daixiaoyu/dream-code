package com.zeye.networkflow

import java.lang
import java.sql.Timestamp


import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.AllWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

//UV信息
case class UvInfo(windowEnd:String,uvCount:Long)

object UVCount {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    env.readTextFile("C:\\Users\\mazhonghua\\Desktop\\Flink上课资料\\myZeye\\src\\main\\resources\\data1.csv").map(line => {
      val fields = line.split(",")
      UserBehavior(fields(0).trim.toLong, fields(1).trim.toLong, fields(2).trim.toInt, fields(3).trim, fields(4).trim.toLong)
    }).assignTimestampsAndWatermarks(new PageViewEventTimeExtractor())
      .filter(_.behavior == "pv")
      .timeWindowAll(Time.hours(1))
      .apply(new UvCountByWindow)
      .print()

    env.execute("uv count....")
  }
}

class UvCountByWindow() extends
  AllWindowFunction[UserBehavior,UvInfo,TimeWindow]{
  override def apply(window: TimeWindow,
                     values: Iterable[UserBehavior],
                     out: Collector[UvInfo]): Unit = {
    //定一个SET集合
    var userIdSet = Set[Long]() //userid = Long = 8字节  10G内存
    for(user <- values){
      userIdSet += user.userId
    }
    out.collect( UvInfo(new Timestamp(window.getEnd).toString,userIdSet.size))
  }
}
