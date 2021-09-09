
package thread.bfbm.container;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *  巴分巴秒官方交流QQ群:750555573
 *
 */
public class Demo04_ArrayBlockingQueue {
	
	final BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
	
	public static void main(String[] args) {
		final Demo04_ArrayBlockingQueue t = new Demo04_ArrayBlockingQueue();
		
		for(int i = 0; i < 5; i++){
			// System.out.println("add method : " + t.queue.add("value"+i));
			/*try {
				t.queue.put("put"+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			//System.out.println("put method : " + i);
			System.out.println("offer method : " + t.queue.offer("value"+i));
			/*try {
				System.out.println("offer method : " + 
							t.queue.offer("value"+i, 1, TimeUnit.SECONDS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		
		System.out.println(t.queue);
	}

}
