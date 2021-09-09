package thread.bfbm.threadpool;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 */
public class Demo07_ResponseService {
    public static String responseMsg(String request){
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return request+":"+getRandomString(8);
    }

    private static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
