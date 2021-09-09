package com.dai.iot.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName HotKey
 * @Description TODO
 * @Author xinyu.dai
 * @Date 2021/8/26 1:59 下午
 * @Version 1.0
 **/
public class HotKey {
    // 这是大概的key 的数量 如 1千万
    private static int KEY_NUM = 100000000;

    // 这是 byte 数组的 size  也就是 KEY_NUM / 8
    private static int BYTE_NUM = KEY_NUM >> 3;

    // 用来存储 热点key 如果是 一亿的 key 则消耗  11.92093 MB 内存
    private byte[] bits = new byte[BYTE_NUM];

    private int[] counts = new int[KEY_NUM];

    private Long lastDate = 0L;

    // 用来统计热点 key 的时间 如 10 秒 ，为了测试方便先写短一点，生产可以长一些
    private int HOT_COMPUTE_SECOND = 10;

    // 用来统计热点 key 的阈值
    private int HOT_COMPUTE_COUNT = 500;


    /*********************测试用例*********************/

    /**
     * 10秒内 请求 500 次 ，断言 为 热点key
     */
    @Test
    public void testRequestHotKey() {
        String key = "daixinyu";
        for (int i = 0; i <= 500; ++i) {
            request(key);
        }
        Assert.assertTrue(isHotKey(key));
    }

    /**
     * 11 秒 之后请求 是否为热点 key ，断言为不是热点
     */
    @Test
    public void testRequestNotHotKeyTimeOut() {
        String key = "daixinyu";
        for (int i = 0; i <= 500; ++i) {
            request(key);
        }
        try {
            TimeUnit.SECONDS.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(isHotKey(key));
    }

    /**
     * 10 秒内 请求 499 次，未达到阈值，断言不是 热点key
     */
    @Test
    public void testRequestNotHotKey() {
        String key = "daixinyu";
        for (int i = 0; i <= 499; ++i) {
            request(key);
        }

        Assert.assertFalse(isHotKey(key));
    }


    /**
     * 测试添加hotkey 是正确的
     */
    @Test
    public void testIsHotKey() {

        add(getPartition("dai"));

        System.out.println(isHotKey("dai"));

        int indexInner = 1;
        for (int i = 0; i < 5; i++) {
            System.out.println("-------" + indexInner++ + "-------");
            showByte(bits[i]);
        }
    }


    /**
     * 测试删除hotkey
     */
    @Test
    public void TestClearIsSuccess() {

        int hash = getPartition("dai");

        System.out.println("hash:" + hash);

        System.out.println("getIndex:" + getIndex(hash));

        add(hash);
        Assert.assertTrue(contains(hash));
        clear(hash);
        Assert.assertFalse(contains(hash));

        int indexInner = 1;

        for (int i = 0; i < 5; i++) {
            System.out.println("-------" + indexInner++ + "-------");
            showByte(bits[i]);
        }
    }


    /*********************实现分割符*********************/

    /**
     * 请求KEY
     *
     * @param key
     */
    public void request(String key) {
        if (timeOut()) {
            // 重置
            this.counts = new int[KEY_NUM];
        }

        int hashPartition = getPartition(key);

        int count = counts[hashPartition];

        if (count >= HOT_COMPUTE_COUNT) {
            System.out.println("写入热点 key:" + key + ",hashPartition:" + hashPartition + ",count:" + counts[hashPartition]);
            //超过阈值，添加到热点key中，且count
            add(hashPartition);
        } else {
            counts[hashPartition] = ++count;
        }
    }

    /**
     * 判断当前key 是否是热点KEY
     *
     * @param key
     * @return
     */
    public boolean isHotKey(String key) {
        int hashPartition = getPartition(key);
        if (timeOut()) {
            clear(hashPartition);
        }
        boolean isHost = contains(hashPartition);
        System.out.println("判断热点key:" + key + ",hashPartition:" + hashPartition + ",结果：" + isHost);
        return isHost;
    }

    /**
     * 这个是参照的MapReduce 的HashPartition ，这样key 的存储可以只用 一个 bit 来存储
     * @param key
     * @return
     */
    public static int getPartition(String key) {
        return getPartition(key, KEY_NUM);
    }

    public static int getPartition(String key, int num) {
        return (key.hashCode() & Integer.MAX_VALUE) % num;
    }

    /**
     * 往位图中当前hash位置 写为 1 , 时间复杂度为 O(1)
     *
     * @param hash
     */
    public void add(int hash) {
        bits[getIndex(hash)] |= 1 << (hash % 8);
    }

    /**
     * 判断当前hash在位图中的位置是否为1  查询的时间复杂度 为 O(1)
     *
     * @param hash
     */
    public boolean contains(int hash) {
        return (bits[getIndex(hash)] & 1 << (hash % 8)) != 0;
    }

    /**
     * 将当前hash在位图中的设置为 0 时间复杂度 为O(1)
     *
     * @param hash
     */
    public void clear(int hash) {
        bits[getIndex(hash)] &= ~(1 << hash % 8);
    }


    public int getIndex(int target) {
        return target >> 3;
    }

    public boolean timeOut() {
        long now = System.currentTimeMillis();

        long second = (now - lastDate) / 1000;

        if (second >= HOT_COMPUTE_SECOND) {
            lastDate = now;
            return true;
        }
        return false;
    }

    /**
     * 显示每一位的值
     * @param b
     */
    public void showByte(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        for (byte b1 : array) {
            System.out.print(b1);
            System.out.print(" ");
        }
        System.out.println();
    }


}
