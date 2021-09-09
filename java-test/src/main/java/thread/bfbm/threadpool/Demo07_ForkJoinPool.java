package thread.bfbm.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 * “分而治之”，递归计算
 */
public class Demo07_ForkJoinPool {
    final static List<String> httpUrls = new ArrayList<>();
    //final static String[] reqsponse = new String[1000000];
    final static List<String> reqsponse = new ArrayList<>();
    static{
        for(int i = 0; i <100; i++){
            httpUrls.add("http://www.taobao.com/item="+i);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        RequestTask task = new RequestTask(httpUrls,0,httpUrls.size()-1);
        Future<List<String>> future = pool.submit(task);
        List<String> a=future.get();
        for (String s : a) {
            System.out.println("结果集中的记录--》"+s);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(a);
        System.out.println("count cost time->"+(endTime-startTime)+"ms");

    }

    static class RequestTask extends RecursiveTask<List<String>> { // RecursiveAction
        int begin, end;
        List<String> urls;

        public RequestTask(List<String> urls, int begin, int end) {
            this.urls = urls;
            this.begin = begin;
            this.end = end;
        }

        //
        protected List<String> compute() {
            int count = end - begin; // 代表当前这个task需要处理多少数据
            // 自行根据业务场景去判断是否是大任务,是否需要拆分
            if (count == 0) {
                String url = httpUrls.get(begin);
                long userinfoTime = System.currentTimeMillis();
                String response = Demo07_ResponseService.responseMsg(url);
                System.out.println(Thread.currentThread() + " 接口调用完毕" + (System.currentTimeMillis() - userinfoTime) + " #" + url);
                List list = new ArrayList();
                list.add(response);
                return list;
            } else { // 如果是多个接口调用,拆分成子任务  7,8,   9,10
                System.out.println(Thread.currentThread() + "任务拆分一次");
                //求中间值。
                int x = (begin + end) / 2;
                //任务从开始，到中间值。
                RequestTask requestTask = new RequestTask(urls, begin, x);// 负责处理哪一部分
                //fork拆分任务。
                requestTask.fork();
                //任务从中间值+1 ，到结束。
                RequestTask requestTask1 = new RequestTask(urls, x + 1, end);// 负责处理哪一部分?
                requestTask1.fork();

                // join获取处理结果
                List list = new ArrayList();
                //join合并结果。
                list.addAll(requestTask.join());
                list.addAll(requestTask1.join());
                return list;
            }
        }

    }
}
