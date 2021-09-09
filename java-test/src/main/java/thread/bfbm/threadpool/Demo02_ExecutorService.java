package thread.bfbm.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * 巴分巴秒官方交流QQ群:750555573
 */
public class Demo02_ExecutorService {
    public static void main(String[] args) {
        ExecutorService service =
                Executors.newFixedThreadPool(5);
        for(int i = 0; i < 6; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " --executor");
                }
            });
        }

        System.out.println(service);
        service.shutdown();
        // 是否已经结束， 相当于回收了资源。
        System.out.println(service.isTerminated());
        // 是否已经关闭， 是否调用过shutdown方法
        System.out.println(service.isShutdown());
        System.out.println(service);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}
