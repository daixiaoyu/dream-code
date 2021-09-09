package thread.bfbm.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 */
public class Demo08_ThreadPoolExecutor {
    public static void main(String[] args) {
        // 参数说明：
        // corePoolSize：核心线程5个，
        // maximumPoolSize：核心线程外最大容量5个，
        // keepAliveTime：线程的生命周期无限。
        ExecutorService service =
                new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
        for(int i = 0; i < 6; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }

        System.out.println(service);

        service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}
