package thread.atomic.AtomicInteger;

import java.util.concurrent.TimeUnit;

public class ReadFileThread extends Thread {

    String fileName;

    public ReadFileThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + ",读取文件:" + fileName);

        try {
            TimeUnit.SECONDS.sleep(1);
            // TODO: 2021/5/14 读取文件的插座，抽象为一个接口
            System.out.println("最大数字:" + 5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


