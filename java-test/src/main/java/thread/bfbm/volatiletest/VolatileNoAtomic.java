package thread.bfbm.volatiletest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  volatile关键字不具备synchronized关键字的原子性（同步）
 */
public class VolatileNoAtomic{
	//private volatile int count;
	private AtomicInteger count = new AtomicInteger(0);
    //private AtomicLong count=new AtomicLong();
	private void addClick(){
		for (int i = 0; i < 10000; i++) {
			//count++ ;
			//count.incrementAndGet();
            count.incrementAndGet();
		}
		System.out.println(Thread.currentThread().getName() + " :点击数=" + count);
	}

	public static void main(String[] args) {
		VolatileNoAtomic volatileNoAtomic=new VolatileNoAtomic();
		List<Thread> threads = new ArrayList<>();
		for(int i = 1; i <=20; i++){
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					volatileNoAtomic.addClick();
				}
			},"文章"+i));
		}
		for(Thread thread : threads){
			thread.start();
		}

		for(Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("点击数:"+volatileNoAtomic.count);
	}




}
