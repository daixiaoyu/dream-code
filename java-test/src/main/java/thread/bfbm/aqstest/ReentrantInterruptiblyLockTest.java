package thread.bfbm.aqstest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 巴分巴秒官方交流QQ群:750555573
 * 可阻塞，中断
 */
public class ReentrantInterruptiblyLockTest {
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
        try{
            lock.lockInterruptibly(); // 可尝试打断，阻塞等待锁。可以被其他的线程打断阻塞状态
            System.out.println("一致的中间查询结果：account = " + this.account + " , amount = " + this.amount);
        }catch(InterruptedException e){
            System.out.println("getValue interrupted");
        }finally{
            try{
                lock.unlock();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) throws InterruptedException {
        final ReentrantInterruptiblyLockTest t = new ReentrantInterruptiblyLockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.setValue("张三", 200);
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(1000);

        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                t.getValue();
            }
        });
        thread1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        thread1.interrupt();
    }
}
