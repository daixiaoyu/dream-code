package thread.bfbm.aqstest;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 巴分巴秒官方交流QQ群:750555573
 * 条件控制
 */
public class ReentrantConditionTest {
    Lock lock = new ReentrantLock();
    final Condition condition  = lock.newCondition();
    private List<Integer> list=new LinkedList<>();
    public  void  setValue(int value){
        try{
            lock.lock();
            while(list.size() ==10) {
                condition.await();
            }
                list.add(value);
                condition.signal();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock(); // 解锁
        }
    }

    public  int  getValue(){
        int value=0;
        lock.lock();
        try {
            while (list.size() == 0) {
                System.out.println("阻塞");
                condition.await();
            }
                value = list.get(0);
                list.remove(0);
                condition.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock(); // 解锁
        }
            return value;
    }

    public static void main(String[] args) throws InterruptedException {
        final ReentrantConditionTest t = new ReentrantConditionTest();
        for (int i = 0; i <10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <5 ; j++) {
                        int value= t.getValue();
                        System.out.println("取值："+value);
                    }
                }
            }).start();
        }


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (int i = 0; i <2 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <25 ; j++) {
                        t.setValue(j);
                        System.out.println("放入值："+j);
                    }
                }

            }).start();
        }
    }
}
