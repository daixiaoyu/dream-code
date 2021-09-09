package thread.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 奈学教育-秋平老师
 **/
public class VolatileTest {

    public static volatile int r = 0;  // 共享变量  堆 heap   可见性 一写多读   原子性？
    public static AtomicInteger i = new AtomicInteger();  // 原子操作保证共享变量的原子性  CAS
    //作业： long  and  double     non-automicity

    //automic

//    private static  Object lock = new Object();
//    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void increase(){

//            r++; //automic

            i.incrementAndGet();

    }

    private static final int THREADS=20;

    public static void main(String[] args) {

        Thread[] threads = new Thread[THREADS];
        for (int i= 0; i<THREADS; i++){
            threads[i] = new Thread( () -> {
                for (int j=0; j<10000; j++){
                    increase();
                }
            });
            threads[i].start();
        }


        while(Thread.activeCount() > 2){
            Thread.yield();
        }

//        for (int i=0; i<THREADS; i++){
//            try {
//                threads[i].join();
//            }catch(Exception e){
//
//            }
//        }

        System.out.println(i);
    }
}
