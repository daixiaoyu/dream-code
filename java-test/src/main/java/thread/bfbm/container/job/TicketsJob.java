package thread.bfbm.container.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 */
public class TicketsJob {
    final static BlockingQueue<String> queue = new ArrayBlockingQueue<>(30);
    final static CountDownLatch countDownLatch=new CountDownLatch(1);
    static {
        for (int i = 1; i <=30 ; i++) {
            queue.offer("火车票座位—>"+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String ticket=queue.poll();
                        if(ticket==null){
                            //System.out.println(Thread.currentThread().getName() +":没有票了");
                        }else{
                            System.out.println(Thread.currentThread().getName() +"买到了："+ticket);
                        }
                }
            },"乘客"+i).start();
        }
        try {
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
