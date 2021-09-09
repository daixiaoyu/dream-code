package thread.bfbm.container.containerSafe;

import java.util.*;
import java.util.concurrent.CountDownLatch;
/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 */
public class MapSafeTest {
    private final static int num = 100;
    final Random r = new Random();
    //final Map<String, String> map = new HashMap<>();
    final Map<String, String> map = new Hashtable<>();
    //final Map<String, String> map = new ConcurrentHashMap<>();
    //final Map<String, String> map = new ConcurrentSkipListMap<>();
    public void addObject(){
        for (int i = 0; i <100000 ; i++) {
            map.put("key"+i, "value"+r.nextInt(100000));
        }
    }

    public int getMapSize(){
        return map.size();
    }

    public static void main(String[] args) {
        MapSafeTest listSafeTest = new MapSafeTest();
        final CountDownLatch countDownLatch = new CountDownLatch(num);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    listSafeTest.addObject();
                    countDownLatch.countDown();
                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("总数" + listSafeTest.getMapSize());
        long end = System.currentTimeMillis();
        System.out.println("执行时间为 ： " + (end-begin) + "毫秒！");
    }
}
