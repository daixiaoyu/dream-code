package thread.bfbm.automictest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicMultiMethodTest {
    private static AtomicInteger count = new AtomicInteger(0);

    public /*synchronized*/ int add(){
        count.addAndGet(1);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count.addAndGet(2);
        count.addAndGet(3);
        count.addAndGet(4); //+10
        return count.get();
    }


    public static void main(String[] args) {

        final AtomicMultiMethodTest au = new AtomicMultiMethodTest();

        List<Thread> ts = new ArrayList<Thread>();
        for (int i = 0; i < 100; i++) {
            ts.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(au.add());
                }
            }));
        }

        for(Thread t : ts){
            t.start();
        }

     for(Thread thread : ts){
           try {
                thread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("总数:"+au.count);
    }
}
