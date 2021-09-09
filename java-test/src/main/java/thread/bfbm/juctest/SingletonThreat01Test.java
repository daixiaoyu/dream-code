package thread.bfbm.juctest;

import java.util.concurrent.CountDownLatch;
/**
 *
 *  巴分巴秒官方交流QQ群:750555573
 */
public class SingletonThreat01Test {
    private static SingletonThreat01Test instance=null;
    private static final int count=1000;
    static CountDownLatch countDownLatch=new CountDownLatch(count);
    static CountDownLatch countDownLatch2=new CountDownLatch(count);
    private SingletonThreat01Test(){}
    private int num=0;

    public static synchronized SingletonThreat01Test getInstance(){
        if(instance==null){
            instance=new SingletonThreat01Test();
        }
        return instance;
    }

    public  int addNum(){
        return num++;
    }

    public  int getNum(){
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
                SingletonThreat01Test singletonTest= SingletonThreat01Test.getInstance();
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
        System.out.println("总数:"+ SingletonThreat01Test.getInstance().getNum());
    }
}
