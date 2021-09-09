package thread.bfbm.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 * 创建一个可缓存线程池，如果线程池长度超过处理需要，
 * 可灵活回收空闲线程，若无可回收，则新建线程。
 */
public class Demo04_CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService threadPoolService = Executors.newCachedThreadPool();
      /*  ExecutorService threadPoolService =
                Executors.newFixedThreadPool(5);*/
        for (int i = 0; i <10 ; i++) {
            try {
                //TimeUnit.SECONDS.sleep(1);
                threadPoolService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("create thread:"+Thread.currentThread().getName());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
