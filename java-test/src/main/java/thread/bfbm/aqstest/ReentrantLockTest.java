package thread.bfbm.aqstest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 巴分巴秒官方交流QQ群:750555573
 * 锁重入
 */
public class ReentrantLockTest {
    Lock lock = new ReentrantLock();
    private String account = "张三";
    private double amount = 100;
    public void setValue(String account, double amount){
        try{
            lock.lock();
            this.account = account;
            RemoteService.handle();
            this.amount = amount;
            System.out.println("实际结算结果：account = " + account + " , amount = " + amount);
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
           lock.unlock(); // 解锁
        }
    }

    public  void getValue(){
        lock.lock();
        System.out.println("中间查询结果：account = " + this.account + " , amount = " + this.amount);
        lock.unlock();
    }



    public static void main(String[] args) throws InterruptedException {
        final ReentrantLockTest t = new ReentrantLockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.setValue("张三", 200);
            }
        }).start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                t.getValue();
            }
        }).start();
    }
}
