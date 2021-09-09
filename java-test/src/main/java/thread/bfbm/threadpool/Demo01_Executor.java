package thread.bfbm.threadpool;

import java.util.concurrent.Executor;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 */
public class Demo01_Executor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }

    public static void main(String[] args) {
        Demo01_Executor demo01_executor=new Demo01_Executor();
        demo01_executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("========要执行的业务======");
            }
        });
    }
}
