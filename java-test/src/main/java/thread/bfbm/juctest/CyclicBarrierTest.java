package thread.bfbm.juctest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 * 等待其他的子线程，且阻塞当前线程，直到所有的子线程到齐后，才开始工作
 * 不受主线程的影响，可多次循环
 * 翻译过来循环的翻过一些障碍物
 */
public class CyclicBarrierTest {
    private final static int num = 9;
    //private static int count = 0;
    private CyclicBarrier barrier;

    public CyclicBarrierTest(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public  void doSomething(){
        for (int i = 0; i <3 ; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.barrier.await();
                System.out.println(Thread.currentThread().getName()+"开始工作项："+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        CyclicBarrier barrier = new CyclicBarrier(3);
        CyclicBarrierTest cyclicBarrierTest=new CyclicBarrierTest(barrier);
        //Long startTime=System.currentTimeMillis();
        for (int i = 0; i <num ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cyclicBarrierTest.doSomething();
                }
            },"操作人员:"+i).start();
        }
     /*   Long endTime=System.currentTimeMillis();
        System.out.println("time:"+(endTime-startTime));*/
    }
}
