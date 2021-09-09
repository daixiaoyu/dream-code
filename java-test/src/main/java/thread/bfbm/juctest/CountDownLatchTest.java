package thread.bfbm.juctest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 *主线程进行控制，等待子线程全部完成一个事件后（countDown计数器到0）,开始继续执行。
 */
public class CountDownLatchTest {
    private final static int num = 10;
    private static int count = 0;
    public static void doSomething(){
        System.out.println(Thread.currentThread().getName()+"开始工作..");
        try {
            TimeUnit.SECONDS.sleep(1);
            count++;
            System.out.println(Thread.currentThread().getName()+"处理了："+count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Long startTime=System.currentTimeMillis();
        final CountDownLatch countDownLatch=new CountDownLatch(num);
        for (int i = 0; i <num ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doSomething();
                    countDownLatch.countDown();
                }
            },"操作人员:"+i).start();
        }
        countDownLatch.await();
        Long endTime=System.currentTimeMillis();
        System.out.println("cost time:"+(endTime-startTime));
    }
}
