package thread.bfbm.automictest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicWarGames extends Thread{
    //private volatile boolean isNotify=false;
    private static AtomicBoolean atomicNotify=new AtomicBoolean(false);
    //private AtomicInteger i = new AtomicInteger(0);
    private int i=0;
    private int count=10;
    public void run() {
        System.out.println("游戏房间开启");
        while (true){
            if(atomicNotify.compareAndSet(true,false)){
                addPlayer();
            }

          /* if(atomicNotify.compareAndSet(true,false)){
              // addPlayer();
               i.incrementAndGet();
               System.out.println("加入人数："+i);
               if(i.get()==count){
                   System.out.println("人已经满了，开始游戏");
                   break;
               }
           }*/
            if(i==count){
                System.out.println("人已经满了，开始游戏");
                break;
            }
        }
    }

   public  void addPlayer(){
          i++;
        System.out.println("加入人数："+i);
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicWarGames warGames=new AtomicWarGames();
        warGames.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i <=10; i++){
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                        warGames.atomicNotify.set(true);
                }
            }));
        }
        for(Thread thread : threads){
            thread.start();
            thread.join();
       /*  try {
             TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

}
