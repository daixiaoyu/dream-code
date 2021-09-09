package thread;

import org.testng.annotations.Test;
import sun.awt.geom.AreaOp;

import java.util.concurrent.*;

public class TestReject {

    private static String threadName = "代欣雨";

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        TestReject.threadName = threadName;
    }

    public static void main(String[] args) {
        TestReject testReject = new TestReject();
        testReject.testRejectAbortPolicy();

        testReject.setThreadName("test");

        System.out.println(testReject.getThreadName());
    }


    public void testRejectAbortPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue(20), new MyThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


        for (int i = 0; i < 100; i++) {
            TestRunnableSleep testRunnableSleep = new TestRunnableSleep(10);
            try {
                threadPoolExecutor.execute(testRunnableSleep);
            } catch (Exception e) {
                System.out.println("提交失败：" + i );
            }

        }

        threadPoolExecutor.shutdown();
    }

    class MyThreadFactory implements ThreadFactory {

        private Integer nowCount = 0;

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(getThreadName() + "-" + getNowCount());
            return thread;
        }

        public synchronized Integer getNowCount() {
            return ++nowCount;
        }

    }
}
