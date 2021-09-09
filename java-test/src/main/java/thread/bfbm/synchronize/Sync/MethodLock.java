package thread.bfbm.synchronize.Sync;

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
