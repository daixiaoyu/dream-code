package thread.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 奈学-肖秋平老师 Eric
 **/
public class CyclicBarrierGirl {

    static class RunningGirl implements Runnable {

        private CyclicBarrier cyclicBarrier;

        public RunningGirl(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 准备跑...");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " 开始跑...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        for (int i = 0; i < 4; i++) {
            new Thread(new RunningGirl(cyclicBarrier), "跑女-" + i + "号").start();
        }
    }

    public static void main(String[] args) {
        CyclicBarrierGirl.cyclicBarrierTest();
    }

}
