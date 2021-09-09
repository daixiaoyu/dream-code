package thread.bfbm.automictest;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicLongArrayTest {
    public static void main(String[] args) {
        //创建一个长度数组
        //AtomicLongArray atomicLongArray = new AtomicLongArray(10);
        AtomicIntegerArray atomicLongArray = new AtomicIntegerArray(10);
        //设置数组中的值
        atomicLongArray.set(8,18);
        atomicLongArray.set(9,19);
        System.out.println("第8个位置中的值为: " + atomicLongArray.get(8));
        System.out.println("数组长度：" + atomicLongArray.length());

        System.out.println("============addAndGet()方法：以原子方式对数据对象加值===============");
        atomicLongArray.addAndGet(8,2);
        System.out.println("第8个位置中的值为: " + atomicLongArray.get(8));

        System.out.println("============compareAndSet()方法：如果当前值 == 预期值，则以原子方式更新给定值===============");
        Boolean bool = atomicLongArray.compareAndSet(8,20,30);
        System.out.println("第8个位置中的值为: " + atomicLongArray.get(8));

        System.out.println("============decrementAndGet()方法：以原子方式先将当前下标的值减1，再获取减1后的结果===============");
        //Long result1 = atomicLongArray.decrementAndGet(8);
        atomicLongArray.decrementAndGet(8);
        System.out.println("第8个位置中的值为: " + atomicLongArray.get(8));

        System.out.println("============getAndAdd()方法：以原子方式先获取当前下标的值，再将当前下标的值加上给定的值===============");
        //Long result2 = atomicLongArray.getAndAdd(8,5);
        atomicLongArray.getAndAdd(8,5);
        System.out.println("第8个位置中的值为: " + atomicLongArray.get(8));

        System.out.println("============getAndDecrement()方法：以原子方式先获取当前下标的值，再对当前下标的值减1===============");
        //Long result3 = atomicLongArray.getAndDecrement(8);
        //System.out.println("result3的值为：" + result3);
        System.out.println("第8个位置中的值为: " + atomicLongArray.get(8));

        System.out.println("============getAndIncrement()方法：以原子方式先获取当前下标的值，再对当前下标的值加1===============");
        //Long result4 = atomicLongArray.getAndIncrement(8);
        //System.out.println("result3的值为：" + result4);
        System.out.println("第8个位置中的值为: " + atomicLongArray.get(8));


    }
}
