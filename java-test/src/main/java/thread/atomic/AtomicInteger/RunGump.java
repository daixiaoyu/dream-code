package thread.atomic.AtomicInteger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RunGump {

    static BlockingQueue workQueue = new LinkedBlockingQueue<>(500);
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
            5, TimeUnit.MINUTES, workQueue);

    public final static Integer FILE_SIZE = 99;
    public final static String FILE_PRE = "file";

    public static void main(String[] args) {

        for (int fileIndex = 1; fileIndex <= FILE_SIZE; fileIndex++) {
            ReadFileThread readFileThread = new ReadFileThread(FILE_PRE + fileIndex);
            threadPoolExecutor.execute(readFileThread);
        }

        threadPoolExecutor.shutdown();

    }


}
