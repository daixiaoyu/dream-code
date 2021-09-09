package thread.bfbm.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 创建一个定长线程池，支持定时及周期性任务执行
 */
public class Demo06_ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        System.out.println(service);
        // 定时完成任务。 scheduleAtFixedRate(Runnable, start_limit, limit, timeunit)
        // runnable - 要执行的任务。
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }, 0, 300, TimeUnit.MILLISECONDS);

    }
}
