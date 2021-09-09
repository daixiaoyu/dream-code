package thread.bfbm.container.base;
import thread.bfbm.container.DefineLock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
/**
 *
 *  巴分巴秒官方交流QQ群:750555573
 *
 */
public class ContainerPractice02 {
    private List<Integer> list=new LinkedList<>();
    private Lock lock = new DefineLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();
    public  void  setValue(int value){
        lock.lock();
        try{
                while(list.size() ==10) {
                    try {
                        producer.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(value);
                System.out.println("放入值："+value);
                consumer.signalAll();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
        lock.unlock();
        }
    }

    public  int  getValue() {
        int value=0;
        lock.lock();
        try {
            while(list.size() == 0){
                System.out.println("阻塞");
                consumer.await();
            }
            value = list.get(0);
            list.remove(0);
            producer.signalAll();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } finally {
            lock.unlock();
        }
            return value;
    }


    public static void main(String[] args) {
        ContainerPractice02 containerPractice01=new ContainerPractice02();
        for (int i = 0; i <10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <5 ; j++) {
                        System.out.println("取值："+containerPractice01.getValue());
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
                        containerPractice01.setValue(j);
                    }
                }
            }).start();
        }
    }
}