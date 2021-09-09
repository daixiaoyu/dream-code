package thread.bfbm.aqstest;

import java.util.concurrent.TimeUnit;

public class RemoteService {
    public static void handle() throws InterruptedException {

            /**
             * 模拟业务延迟了
             * 这里定义一个远程调用接口加延迟
             */
        TimeUnit.MILLISECONDS.sleep(5000);
    }
}
