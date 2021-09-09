
package thread.bfbm.juctest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 *  巴分巴秒官方交流QQ群:750555573
 *
 */
public class  ConcurrentCountDownLatchTest {
    private static ConcurrentCountDownLatchTest instance=null;
    private static final int count=1000;
    //static CountDownLatch countDownLatch=new CountDownLatch(count);
    static CountDownLatch countDownLatch1=new CountDownLatch(1);
    private ConcurrentCountDownLatchTest(){}
    private int num=0;

    public static synchronized ConcurrentCountDownLatchTest getInstance(){
        if(instance==null){
            instance=new ConcurrentCountDownLatchTest();
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
                    if(countDownLatch1.getCount()>0){
                        System.out.println("====");
                    }
                    countDownLatch1.await();
                    //countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ConcurrentCountDownLatchTest singletonTest= ConcurrentCountDownLatchTest.getInstance();
                //SingletonTest.addNum();
                //System.out.println(Thread.currentThread().getName()+"singletonTest1:"+singletonTest);
                System.out.println("num+:"+singletonTest.addNum());
            }).start();
            //System.out.println("cout:"+countDownLatch.getCount());
            //countDownLatch.countDown();
        }
        countDownLatch1.countDown();
       try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("总数:"+ ConcurrentCountDownLatchTest.getInstance().getNum());
    }
}
