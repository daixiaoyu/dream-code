package thread;

import java.util.concurrent.TimeUnit;

public class TestRunnableSleep implements Runnable {

    private int sleepTime;

    public TestRunnableSleep(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        String name = thread.getName();

        System.out.println("线程:" + name + "开始执行了，即将睡眠:" + sleepTime + "秒");

        try {

            TimeUnit.SECONDS.sleep(sleepTime);
            System.out.println("线程:" + name + "执行成功结束");

        } catch (InterruptedException e) {
            System.out.println("线程:" + name + "执行失败结束,异常：" + e);
        }
    }
}
