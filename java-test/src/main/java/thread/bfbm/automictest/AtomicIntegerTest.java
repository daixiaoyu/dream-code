package thread.bfbm.automictest;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger=new AtomicInteger(0);
        atomicInteger.getAndAdd(1);
        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger.get());
}
}
