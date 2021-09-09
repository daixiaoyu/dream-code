package thread.bfbm.automictest;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 *
 * AtomicReference和AtomicInteger非常类似，不
 * 同之处就在于AtomicInteger是对整数的封装，底层采用的是compareAndSwapInt实现CAS，比较的是数值是否相等，
 * 而AtomicReference则对应普通的对象引用，底层使用的是compareAndSwapObject实现CAS，
 * 比较的是两个对象的地址是否相等。也就是它可以保证你在修改对象引用时的线程安全性。
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReferenceTest oldReference=new AtomicReferenceTest();
        System.out.println("===oldReference==="+oldReference);
        AtomicReferenceTest newReference=new AtomicReferenceTest();
        System.out.println("===newReference==="+newReference);
        AtomicReference<AtomicReferenceTest> atomicReference=new AtomicReference<AtomicReferenceTest>(oldReference);
        atomicReference.compareAndSet(oldReference,newReference);
        System.out.println("===changeReference==="+atomicReference.get());

    }



}
