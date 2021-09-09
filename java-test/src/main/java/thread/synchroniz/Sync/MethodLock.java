package thread.synchroniz.Sync;
/**
 *
 *  巴分巴秒官方交流QQ群:750555573
 *
 *  @Author:Tony
 *  @Create Date:2019/10/05
 */
public class MethodLock {
    public synchronized void method1(){
        System.out.println("===method1========");
        while (true){

        }
    }

    public synchronized void method2(){
        System.out.println("===method2========");
    }

    public void method3(){
        System.out.println("===method3========");
    }

    public static void main(String[] args) {
        MethodLock methodLock=new MethodLock();
        Thread thread1=new Thread(()->{
            methodLock.method1();
        });
        Thread thread2=new Thread(()->{
            methodLock.method3();
        });
        thread1.start();
        thread2.start();
    }
}
