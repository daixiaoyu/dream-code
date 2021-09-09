package thread.bfbm.aqstest;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * 巴分巴秒官方交流QQ群:750555573
 * 条件
 */
public class ReentrantReadWriteTest {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private int num;
    public  void  setValue(int value){
        try{
            lock.writeLock().lock();
            num=value;
            TimeUnit.MILLISECONDS.sleep(500);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.writeLock().unlock(); // 解锁
        }
    }

    public  void  getValue(){
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读取到的值："+num);
            TimeUnit.MILLISECONDS.sleep(500);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.readLock().unlock(); // 解锁
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ReentrantReadWriteTest t = new ReentrantReadWriteTest();
        for (int i = 0; i <1 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <25 ; j++) {
                        t.setValue(j);
                        System.out.println(Thread.currentThread().getName()+"放入值："+j);
                    }
                }

            }).start();
        }


        for (int i = 0; i <3 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <25 ; j++) {
                        t.getValue();
                    }
                }
            }).start();
        }
    }
}
