package thread.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author 向寒 奈学教育
 **/
public class ArrayListTest {
    private static List<Integer> list = new ArrayList<Integer>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(1000);

    private static class IncreaseTask extends Thread{
        @Override
        public void run() {
            System.out.println("ThreadId:" + Thread.currentThread().getId() + " start!");
            for(int i =0; i < 100; i++){
                list.add(i);
            }
            System.out.println("ThreadId:" + Thread.currentThread().getId() + " finished!");
        }
    }

    public static void main(String[] args){
        for(int i=0; i < 1000; i++){
            executorService.submit(new IncreaseTask());
        }
        executorService.shutdown();
        while (!executorService.isTerminated()){
            try {
                Thread.sleep(1000*10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("All task finished!");
        System.out.println("预计存储大小为："+1000 * 100);
        System.out.println("list size is :" + list.size());
    }
}
