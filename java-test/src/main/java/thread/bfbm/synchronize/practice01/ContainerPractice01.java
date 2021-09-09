package thread.bfbm.synchronize.practice01;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class ContainerPractice01 {
    private List<Integer> list=new LinkedList<>();
    private Object o=new Object();
    public void  setValue(int value){
        synchronized (o) {
            while (list.size() == 100) {
                try {
                    System.out.println("写入阻塞");
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(value);
            o.notifyAll();
        }
    }

    public  int  getValue(){
                 int value=0;
                 synchronized (o) {
                     while (list.size() == 0) {
                         try {
                             System.out.println("取值阻塞");
                             o.wait();
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                     }
                     value = list.get(0);
                     list.remove(0);
                     o.notifyAll();
                 }
                return value;
    }

    public static void main(String[] args) {
        ContainerPractice01 containerPractice01=new ContainerPractice01();
        for (int i = 0; i <1000 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <25 ; j++) {
                        int value= containerPractice01.getValue();
                        System.out.println("->取值："+value);
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

        for (int i = 0; i <25 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <1000 ; j++) {
                        containerPractice01.setValue(j);
                        System.out.println("->放入值："+j);
                    }
                }

            }).start();
        }
    }
}