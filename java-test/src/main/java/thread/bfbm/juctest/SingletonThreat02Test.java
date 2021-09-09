package thread.bfbm.juctest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 *  巴分巴秒官方交流QQ群:750555573
 *
 */
public class SingletonThreat02Test {
    private static SingletonThreat02Test instance=null;
    private static final int count=1000;
    static CountDownLatch countDownLatch=new CountDownLatch(count);
    static CountDownLatch countDownLatch2=new CountDownLatch(count);
    AtomicInteger atomicInteger=new AtomicInteger();
    private SingletonThreat02Test(){}
    private int num;

    public static synchronized SingletonThreat02Test getInstance(){
        if(instance==null){
            instance=new SingletonThreat02Test();
        }
        return instance;
    }

    public   int addNum(){
        return atomicInteger.getAndIncrement();
        //return num++;
    }

    public   int getNum(){
        return atomicInteger.get();
        //return num;
    }

    public static void main(String[] args) {
        for (int i = 0; i <count ; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SingletonThreat02Test singletonTest= SingletonThreat02Test.getInstance();
                //SingletonTest.addNum();
                //System.out.println(Thread.currentThread().getName()+"singletonTest1:"+singletonTest);
                System.out.println("num+:"+singletonTest.addNum());
                countDownLatch2.countDown();
            }).start();
            countDownLatch.countDown();
        }
        try {
            countDownLatch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("总数:"+ SingletonThreat02Test.getInstance().getNum());
    }
}
