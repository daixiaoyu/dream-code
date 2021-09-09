package thread.bfbm.synchronize.safetest;

import java.util.concurrent.TimeUnit;
/**
 * 线程安全模拟-资源共享导致结果不一致
 */
public class PrepareSafe {
    private int count=0;
    public  void  methodSync(){
            count++;
        System.out.println(Thread.currentThread().getName()
                + " :count = " + count);
    }

    public static void main(String[] args) {
        PrepareSafe threadSafe = new PrepareSafe();
        for (int i = 0; i <1000 ; i++) {
            Thread thread=new Thread(()->{
                threadSafe.methodSync();
            });
            thread.start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
