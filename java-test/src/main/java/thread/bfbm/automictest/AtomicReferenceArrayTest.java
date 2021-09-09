package thread.bfbm.automictest;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * AtomicReferenceArray类提供了可以原子读取和写入的底层引用数组的操作，并且还包含高级原子操作。
 */
public class AtomicReferenceArrayTest {
    public static void main(String[] args) {
        //创建一个长度数组
        AtomicReferenceArray<Integer> atomicReferenceArray = new AtomicReferenceArray<>(10);
        //设置数组中的值
        atomicReferenceArray.set(8,18);
        System.out.println("第8个位置中的值为: " + atomicReferenceArray.get(8));

        AtomicReferenceArrayTest oldReference=new AtomicReferenceArrayTest();
        System.out.println("===oldReference==="+oldReference);
        AtomicReferenceArrayTest newReference=new AtomicReferenceArrayTest();
        System.out.println("===newReference==="+newReference);
        AtomicReferenceArray<AtomicReferenceArrayTest> atomicReferenceArray1 = new AtomicReferenceArray<>(10);
        atomicReferenceArray1.set(8,oldReference);
        System.out.println("第8个位置中的实例对象为: " + atomicReferenceArray1.get(8));
        atomicReferenceArray1.compareAndSet(8,oldReference,newReference);
        System.out.println("更改后第8个位置中的实例对象为: " + atomicReferenceArray1.get(8));
    }
}
