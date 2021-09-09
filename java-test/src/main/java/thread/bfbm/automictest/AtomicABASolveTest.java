package thread.bfbm.automictest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicABASolveTest {
    private final static String oldValue="1";
    private final static String newValue="2";
    public final static AtomicStampedReference<String> ATOMIC_REFERENCE = new AtomicStampedReference <String>(oldValue,0);
    public static void main(String []args) {
        for(int i = 0 ; i < 100 ; i++) {
            final int stamp = ATOMIC_REFERENCE.getStamp();
            new Thread() {
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ATOMIC_REFERENCE.compareAndSet(oldValue , newValue,stamp,stamp+1);
                    String value=ATOMIC_REFERENCE.getReference();
                    System.out.println("线程"+Thread.currentThread().getName()+":修改了值"+value);

                }
            }.start();
        }
        new Thread() {
            public void run() {
                final int stamp = ATOMIC_REFERENCE.getStamp();
                while(!ATOMIC_REFERENCE.compareAndSet(newValue, oldValue,stamp,stamp+1));
                String value=ATOMIC_REFERENCE.getReference();
                System.out.println("线程"+Thread.currentThread().getName()+":中间修改了值"+value);
            }
        }.start();
    }

}
