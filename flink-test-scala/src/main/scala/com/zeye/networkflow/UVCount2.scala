//package com.zeye.networkflow
//
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.windowing.triggers.{Trigger, TriggerResult}
//import org.apache.flink.streaming.api.windowing.windows.TimeWindow
//import org.apache.flink.util.Collector
//import redis.clients.jedis.Jedis
//
//object UVCount2 {
//
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    env.setParallelism(1)
//
//    env.readTextFile("C:\\Users\\mazhonghua\\Desktop\\Flink上课资料\\myZeye\\src\\main\\resources\\data1.csv").map(line => {
//      val fields = line.split(",")
//      UserBehavior(fields(0).trim.toLong, fields(1).trim.toLong, fields(2).trim.toInt, fields(3).trim, fields(4).trim.toLong)
//    }).assignTimestampsAndWatermarks(new PageViewEventTimeExtractor())
//      .filter(_.behavior == "pv")
//      .map( data => ("key",data.userId))
//      .keyBy(_._1)
//      .timeWindow(Time.hours(1))
//      .trigger(new MyTrigger)
//      .process(new UvCountByBloom)
//      .print()
//
//    env.execute("UVCount2")
//
//  }
//
//}
//
//class Bloom(size:Long) extends Serializable{
//  //默认的大小是16M
//  //1024 * 1024 * 16
//  private val cap = if(size > 0) size else 1 << 23
//
//  def hash(value:String,seed:Int):Long = {
//    var result = 0L
//    for( i <- 0 until value.length){
//      result = result * seed + value.charAt(i)
//    }
//    result & (cap - 1)
//  }
//
//}
//
//class UvCountByBloom() extends ProcessWindowFunction[(String,Long),UvInfo,String,TimeWindow]{
//  lazy val jedis =new Jedis("localhost",6379)
//  lazy val bloom = new Bloom(1 << 23)
//  override def process(key: String, context: Context,
//                       elements: Iterable[(String, Long)],
//                       out: Collector[UvInfo]): Unit = {
//    //位图索引的key
//    val windowEnd = context.window.getEnd.toString
//    var count = 0L
//    //在redis中存入 count的表
//    if(jedis.hget("count",windowEnd) != null){
//      count= jedis.hget("count",windowEnd).toLong
//    }
//    //获取到用户ID
//    val userId = elements.last._2.toString
//
//    val offset = bloom.hash(userId,56)
//
//    val isExist = jedis.getbit(windowEnd,offset)
//    if(!isExist){
//      jedis.setbit(windowEnd,offset,true)
//      jedis.hset("count",windowEnd,(count+1).toString)
//      out.collect(UvInfo(windowEnd,count + 1))
//    }else{
//      out.collect(UvInfo(windowEnd,count))
//    }
//
//  }
//}
//
//class MyTrigger() extends Trigger[(String,Long),TimeWindow]{
//  override def onElement(element: (String, Long),
//                         timestamp: Long, window: TimeWindow,
//                         ctx: Trigger.TriggerContext): TriggerResult = TriggerResult.CONTINUE
//
//  override def onProcessingTime(time: Long, window: TimeWindow,
//                                ctx: Trigger.TriggerContext): TriggerResult = {
//    //每来一条数据，就直接触发窗口的操作，并清空状态
//    TriggerResult.FIRE_AND_PURGE
//  }
//
//  override def onEventTime(time: Long, window: TimeWindow,
//                           ctx: Trigger.TriggerContext): TriggerResult = TriggerResult.CONTINUE
//
//  override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = {
//
//  }
//}
