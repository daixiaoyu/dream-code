package thread.bfbm.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
 * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class Demo05_SingleThreadExecutor {
    private int num=1;

    public void setNum(){
        num++;
    }

    public int getNum() {
        return num;
    }

    public static void main(String[] args) {
        ExecutorService threadPoolService = Executors.newSingleThreadExecutor();
        Demo05_SingleThreadExecutor demo05_singleThreadExecutor=new Demo05_SingleThreadExecutor();
        for (int i = 0; i <50 ; i++) {
            try {
                //TimeUnit.SECONDS.sleep(1);
                threadPoolService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("获取当前值:"+demo05_singleThreadExecutor.getNum());
                        System.out.println("create thread:"+Thread.currentThread().getName());
                        demo05_singleThreadExecutor.setNum();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
