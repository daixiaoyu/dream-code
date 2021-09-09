package thread.bfbm.juctest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 *  巴分巴秒官方交流QQ群:750555573
 */
public class SingletonThreat03Test {
    private static SingletonThreat03Test instance=null;
    private static final int count=1000;
    static CountDownLatch countDownLatch=new CountDownLatch(count);
    static CountDownLatch countDownLatch2=new CountDownLatch(count);
    static Lock lock = new ReentrantLock();
    private SingletonThreat03Test(){}
    private int num;
    public static synchronized SingletonThreat03Test getInstance(){
        if(instance==null){
            instance=new SingletonThreat03Test();
        }
        return instance;
    }

    public  int addNum(){
        return num++;
    }

    public   int getNum(){
        return num;
    }

    public static void main(String[] args) {
        for (int i = 0; i <count ; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SingletonThreat03Test singletonTest= SingletonThreat03Test.getInstance();
                try{
                    lock.lock();
                System.out.println("num+:"+singletonTest.addNum());
                }finally{
                    lock.unlock(); // 解锁
                }
                countDownLatch2.countDown();
            }).start();
            countDownLatch.countDown();
        }
        try {
            countDownLatch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("总数:"+ SingletonThreat03Test.getInstance().getNum());
    }
}
