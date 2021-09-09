package thread.bfbm.threadpool;

import java.util.concurrent.*;

/**
 * 巴分巴秒官方交流QQ群:750555573
 */
public class Demo03_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Customer> future=service.submit(new Callable<Customer>(){
            @Override
            public Customer call() {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Customer customer=new Customer("test",18);
                return customer;
            }
        });
        System.out.println("===取得线程的返回值==》"+future.get().toString());
    }

}

class Customer{
    private String name;
    private int age;
    public Customer(String name,int age){
        this.name=name;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "姓名："+name+"年龄"+age;
    }
}
