package thread.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 奈学-肖秋平老师 Eric
 **/
public class CountDownLatchMan {
    public static void countDownTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(new RunningMan(countDownLatch), "跑男-" + i + "号").start();
        }

        countDownLatch.await();
        System.out.println("比赛结束：" + countDownLatch.getCount());
    }

    static class RunningMan implements Runnable {

        private CountDownLatch countDownLatch;

        public RunningMan(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            try {
                System.out.println(Thread.currentThread().getName() + " 开始跑...");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " 到达终点...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchMan.countDownTest();
    }


}
