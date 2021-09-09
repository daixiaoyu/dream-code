package thread.bfbm;
import java.util.concurrent.TimeUnit;

public class SimpleThreadLocal {
    //private ArrayList<String> tl = new ArrayList<>();
    private ThreadLocal<String> tl = new ThreadLocal<>();
    public void setValue(String value){
        System.out.println(value);
        tl.set(value);
        //tl.add(0,value);
    }

    public String getValue(){
        //System.out.println("线程名称："+Thread.currentThread().getName());
        //String returnValue=tl.get(0);
        String returnValue=tl.get();
        return returnValue;
    }

    public static void main(String[] args) {
        SimpleThreadLocal simpleThreadLocal=new SimpleThreadLocal();
        new Thread(new Runnable() {
            @Override
            public void run() {
                simpleThreadLocal.setValue("线程一写入");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第一个线程读出value："+simpleThreadLocal.getValue());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("第二个线程读出value："+simpleThreadLocal.getValue());
                simpleThreadLocal.setValue("线程二写入");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("第三个线程读出value："+simpleThreadLocal.getValue());
            }
        }).start();
    }
}
