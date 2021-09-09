package thread.ReentrantLock.ReentrantLockTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {

    private Lock lock = new ReentrantLock();

    public void testMethod() {
        //调用ReentrantLock对象的lock()方法获取锁
        lock.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("ThreadName=" + Thread.currentThread().getName()
                        + (" " + (i + 1)));
            }
            //释放锁
        }finally {
            lock.unlock();
        }


    }

}