//package com.zeye.product
//
//import java.sql.Timestamp
//
//import org.apache.flink.api.common.functions.AggregateFunction
//import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
//import org.apache.flink.configuration.Configuration
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.functions.{AssignerWithPeriodicWatermarks, KeyedProcessFunction}
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.streaming.api.scala.function.WindowFunction
//import org.apache.flink.streaming.api.watermark.Watermark
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.windowing.windows.TimeWindow
//import org.apache.flink.util.Collector
//
//import scala.collection.mutable.ListBuffer
//
//
////用户输入的数据
//case class UserBehavior(userId:Long,productId:Long,categoryId:Int,
//                        behavior:String,timestamp:Long)
////定义窗口聚合样例类
//case class  ProductViewCount(productId:Long,windowEnd:Long,count:Long)
///**
// * 热门商品分析
// */
//object HotProduct {
//  def main(args: Array[String]): Unit = {
//
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setParallelism(1)
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//
//    val UserDataStream = env.readTextFile("C:\\Users\\mazhonghua\\Desktop\\Flink上课资料\\myZeye\\src\\main\\resources\\data1.csv")
//
//    val dataStream = UserDataStream.map(line => {
//      val fields = line.split(",")
//      UserBehavior(fields(0).trim.toLong, fields(1).trim.toLong, fields(2).trim.toInt, fields(3).trim, fields(4).trim.toLong)
//    }).assignTimestampsAndWatermarks(new EventTimeExtractor)
//
//
//    //处理数据
//    val result = dataStream.filter(_.behavior == "pv")
//      .keyBy(_.productId)
//      .timeWindow(Time.hours(1), Time.minutes(5))
//      .aggregate(new CountAgg(), new WindowResult)
//      .keyBy(_.windowEnd)
//      .process(new TopHotProduct(3))
//
//
//    //打印输出
//    result.print()
//
//    env.execute("HotProduct")
//  }
//
//}
//
///**
// * 自定义处理函数
// * @param topN
// */
//class TopHotProduct(topN:Int) extends KeyedProcessFunction[Long,ProductViewCount,String]{
//  private var productState:ListState[ProductViewCount] = _
//
//  override def open(parameters: Configuration): Unit = {
//    productState = getRuntimeContext.getListState(
//      new ListStateDescriptor[ProductViewCount]("product-state",classOf[ProductViewCount])
//    )
//  }
//  override def processElement(  value: ProductViewCount,
//                                ctx: KeyedProcessFunction[Long,
//                                ProductViewCount, String]#Context,
//                                out: Collector[String]): Unit = {
//
//    productState.add(value)
//    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
//
//  }
//
//  override def onTimer(timestamp: Long,
//                       ctx: KeyedProcessFunction[Long, ProductViewCount, String]#OnTimerContext,
//                       out: Collector[String]): Unit = {
//    var allProduct:ListBuffer[ProductViewCount]= new ListBuffer()
//
//    val iterator = productState.get().iterator()
//    while(iterator.hasNext){
//      allProduct += iterator.next()
//    }
//
//    //TopN
//    val sortedProuct = allProduct.sortWith(_.count > _.count) //降序
//      .take(topN)
//
//    productState.clear()
//
//    val sb = new StringBuilder()
//    sb.append("时间：").append(new Timestamp(timestamp - 1)).append("\n")
//    var number = 0
//    sortedProuct.foreach( product =>{
//      sb.append(" 商品ID：").append(product.productId)
//        .append(" 商品浏览量=").append(product.count)
//        .append("\n")
//    })
//
//    sb.append("============================")
//    out.collect(sb.toString())
//
//  }
//}
//
///**
// *自定义窗口函数
// */
//class WindowResult() extends WindowFunction[Long,ProductViewCount,Long,TimeWindow]{
//  override def apply(key: Long, window: TimeWindow,
//                     input: Iterable[Long],
//                     out: Collector[ProductViewCount]): Unit = {
//    out.collect(ProductViewCount(key,window.getEnd,input.iterator.next()))
//  }
//}
//
///**
// * 自定义聚合函数
// */
//class CountAgg() extends AggregateFunction[UserBehavior,Long,Long]{
//  override def createAccumulator(): Long = 0L
//
//  override def add(in: UserBehavior, acc: Long): Long = acc + 1
//
//  override def getResult(acc: Long): Long = acc
//
//  override def merge(acc: Long, acc1: Long): Long =  acc + acc1
//}
//
///**
// * 定义waterMark
// */
//class EventTimeExtractor extends AssignerWithPeriodicWatermarks[UserBehavior]{
//
//   var currentMaxEventTime = 0L
//   val maxOufOfOrderness = 10000 //最大乱序时间
//
//  override def getCurrentWatermark: Watermark = {
//    new Watermark(currentMaxEventTime - maxOufOfOrderness)
//  }
//
//  override def extractTimestamp(element: UserBehavior, previousElementTimestamp: Long): Long = {
//    //时间字段
//    val timestamp = element.timestamp * 1000
//
//    currentMaxEventTime = Math.max(element.timestamp, currentMaxEventTime)
//    timestamp;
//  }
//}
