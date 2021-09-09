
package thread.bfbm.container;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 *  巴分巴秒官方交流QQ群:750555573
 *
 */
public class Demo03_LinkedBlockingQueue {
	
	final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	final Random r = new Random();
	
	public static void main(String[] args) {
		final Demo03_LinkedBlockingQueue t = new Demo03_LinkedBlockingQueue();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						t.queue.put("value"+t.r.nextInt(1000));
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "producer").start();
		
		for(int i = 0; i < 3; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							System.out.println(Thread.currentThread().getName() + 
									" - " + t.queue.take());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}, "consumer"+i).start();
		}
	}

}
