package thread.bfbm.automictest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicABATest {
    private final static String oldValue="1";
    private final static String newValue="2";
    public final static AtomicReference <String>ATOMIC_REFERENCE = new AtomicReference<String>(oldValue);

    public static void main(String []args) {
        for(int i = 0 ; i < 10 ; i++) {
            new Thread() {
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(ATOMIC_REFERENCE.compareAndSet(oldValue , newValue))
                    System.out.println("线程"+Thread.currentThread().getName()+":修改了值"+ATOMIC_REFERENCE.get());

                }
            }.start();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        new Thread() {
            public void run() {
                while(!ATOMIC_REFERENCE.compareAndSet(newValue, oldValue));
                System.out.println("线程"+Thread.currentThread().getName()+":中间修改了值"+ATOMIC_REFERENCE.get());
            }
        }.start();
    }

}
