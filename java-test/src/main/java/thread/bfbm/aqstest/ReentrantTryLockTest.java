package thread.bfbm.aqstest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 巴分巴秒官方交流QQ群:750555573
 * 尝试获取锁
 */
public class ReentrantTryLockTest {
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
        boolean isLocked = false;
        try{
            //isLocked=lock.tryLock();
            //通过调整时间参数，不断去获取锁，如果2秒后还拿不到，返回false
             isLocked = lock.tryLock(5, TimeUnit.SECONDS);
            if(isLocked){
                System.out.println("一致的中间查询结果：account = " + this.account + " , amount = " + this.amount);
            }else{
                System.out.println("不一致的中间查询结果：account = " + this.account + " , amount = " + this.amount);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(isLocked){
                // 尝试锁在解除锁标记的时候，一定要判断是否获取到锁标记。
                // 如果当前线程没有获取到锁标记，会抛出异常。
                lock.unlock();
            }
        }
    }



    public static void main(String[] args) throws InterruptedException {
        final ReentrantTryLockTest t = new ReentrantTryLockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.setValue("张三", 200);
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                t.getValue();
            }
        }).start();
    }
}
