package thread.bfbm.synchronize.Sync;

import java.util.concurrent.TimeUnit;

public class SyncUse {
    private int count=0;
    /**
     * 临界资源对象，多个线程可访问到的
     */
    private Object o=new Object();

    /**
     * 方法加锁，锁的是当前对象
     */
    public  synchronized void  methodSync(){
        count++;
        System.out.println(Thread.currentThread().getName()
                + " :count = " + count);
    }

    public   void  methodSync1(){
        System.out.println(Thread.currentThread().getName()
                + " :methodSync1（）count = " + count);
    }

    /**
     * 临界资源对象加锁，锁的是临界资源对象
     */
    public void resourceSync(){
        synchronized(o){
            count++;
            System.out.println(Thread.currentThread().getName()
                    + " :count = " + count);
        }
    }

    /**
     * this对象加锁，锁的是当前对象
     */
    public void thisSync(){
        synchronized(this){
            count++;
            System.out.println(Thread.currentThread().getName()
                    + " :count = " + count);
        }
    }

    private static int staticCount=0;

    /**
     * 静态方法加锁，锁的是class对象
     */
    public static synchronized void staticSync(){
        staticCount++;
        System.out.println(Thread.currentThread().getName()
                + " :staticCount = " + staticCount);
    }

    /**
     * 静态方法代码块加锁，锁的是class对象
     */
    public static synchronized void thisStaticSync(){
        synchronized (SyncUse.class){
            staticCount++;
            System.out.println(Thread.currentThread().getName()
                    + " :staticCount = " + staticCount);
        }
    }

    public static void main(String[] args) {
        SyncUse syncImpl = new SyncUse();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.methodSync();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.methodSync();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.methodSync();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.methodSync();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.methodSync();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.methodSync1();
            }
        }).start();

      try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("=======静态方法=======");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.staticSync();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncImpl.staticSync();
            }
        }).start();

    }
}
