package thread.bfbm.volatiletest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class  VolatileWarGames extends Thread{
    private boolean isNotify=false;
    private int i=0;
    private int count=10;
    public void run() {
        System.out.println("游戏房间开启");
        while (true){
            if(isNotify==true){
                addPlayer();
            }
            if(i==count){
                System.out.println("人已经满了，开始游戏");
                break;
            }
        }
    }

    public  void addPlayer(){
          i++;
        System.out.println("加入人数："+i);
        isNotify=false;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileWarGames warGames=new VolatileWarGames();
        warGames.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i <=10; i++){
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    warGames.isNotify = true;
                }
            }));
        }
        for(Thread thread : threads){
            thread.start();
            //thread.join();
           try {
               TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
