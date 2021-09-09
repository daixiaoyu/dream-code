package thread.bfbm.synchronize.base;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ChangshaRemoteService {
    public static int getData(){
        long startTime = System.currentTimeMillis();
        int sum=0;
        for(int i = 0; i < 400; i++)
        {
            int data=new Random().nextInt(10);
            sum = sum + data;
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("changsha: cost time->"+(endTime-startTime)+"ms");
        return sum;
    }

}
