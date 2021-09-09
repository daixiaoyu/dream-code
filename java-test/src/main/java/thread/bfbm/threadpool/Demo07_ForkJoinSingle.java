package thread.bfbm.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 */
public class Demo07_ForkJoinSingle {
    final static List<String> httpUrls = new ArrayList<>();
    final static List<String> responses = new ArrayList<>();
    final static int MAX_SIZE = 50000;

    static {
        for (int i = 0; i < 100; i++) {
            httpUrls.add("http://www.taobao.com/item=" + i);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        for (String httpUrl : httpUrls) {
            String response = Demo07_ResponseService.responseMsg(httpUrl);
            responses.add(response);
            System.out.println("结果集中的记录--》" + response);
        }
        /*for (String respons : responses) {
            System.out.println("结果集中的记录--》" + respons);
        }*/
        long endTime = System.currentTimeMillis();
        System.out.println(responses);
        System.out.println("count cost time->" + (endTime - startTime) + "ms");


    }
}
