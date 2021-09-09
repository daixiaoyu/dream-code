package thread.bfbm.container.containerSafe;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 */
public class ListSafeTest {
    private final static int num = 100;
    //List<Object> list = new ArrayList<Object>();
    //List<Object> list = new Vector<>();
    List<Object> list = new CopyOnWriteArrayList<>();
    public void addObject(){
        for (int i = 0; i <1000 ; i++) {
            list.add(new Object());
        }
    }

    public int getListSize(){
        return list.size();
    }

    public static void main(String[] args) {
        ListSafeTest listSafeTest = new ListSafeTest();
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
        long end = System.currentTimeMillis();
        System.out.println("执行时间为 ： " + (end-begin) + "毫秒！");
        System.out.println("总数:" + listSafeTest.getListSize());
    }
}
