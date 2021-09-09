# 项目构建
* 使用 Intellij Idea 以 Maven 项目导入本项目
* 构建项目需要网络下载依赖 Flink 等三方jar包
* 使用 mvn clean compile 命令编译项目


# 项目运行
* 确认 iot/resources 包在classpath 下
* 运行com.dai.iot.IotMain 类 下的 main 方法
* 如果项目报错 iot.txt 文件无法找到(我在家里windows电脑上会发生此问题),
请将 /resources/iot.txt 的绝对路径复制给 IotMain 的 INPUT_TXT 属性
  
# 项目使用技术
由于需求是实时监控场景，于是我选择了当下比较适合的Flink开源技术进行并行处理
由于Flink并行监控油品质量传感器和温度传感器，所以在结果展现是有先后打印差别

# 结语
由于各种原因，程序自测没有非常充分，如有bug非常抱歉




