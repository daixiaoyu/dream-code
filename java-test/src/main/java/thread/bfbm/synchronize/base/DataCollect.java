package thread.bfbm.synchronize.base;

import java.util.concurrent.CountDownLatch;

public class DataCollect {
    private static int count1,count2,count3;
    public static void main(String[] args) {
      /*  long startTime = System.currentTimeMillis();
        int num1=BeijingRemoteService.getData();
        int num2=ShanghaiRemoteService.getData();
        int num3=ChangshaRemoteService.getData();
        long endTime = System.currentTimeMillis();
        int sum=num1+num2+num3;
        System.out.println("sum->"+sum);
        System.out.println("count cost time->"+(endTime-startTime)+"ms");*/

        CountDownLatch countDownLatch=new CountDownLatch(3);
       long startTime = System.currentTimeMillis();
        Thread thread1=new Thread(()->{
            count1=BeijingRemoteService.getData();
            countDownLatch.countDown();
        });

        Thread thread2=new Thread(()->{
            count2=ShanghaiRemoteService.getData();
            countDownLatch.countDown();
        });

        Thread thread3=new Thread(()->{
            count3=ChangshaRemoteService.getData();
            countDownLatch.countDown();
        });
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        int sum=count1+count2+count2;
        System.out.println("sum->"+sum);
        System.out.println("count cost time->"+(endTime-startTime)+"ms");
    }
}
