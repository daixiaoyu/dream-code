package thread.bfbm.synchronize;

public class RemoteService {
    public static void handle(){
        try {
            /**
             * 模拟业务延迟了
             * 这里定义一个远程调用接口加延迟
             */
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
