
package thread.bfbm.container;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 *  巴分巴秒官方交流QQ群:750555573
 */
public class Demo02_QueueAndBlockingComp {
	private Queue<Integer> queue = new ConcurrentLinkedQueue<>();
	final BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
	public  void  setValue(int Integer){
		//queue.offer(value);
		try {
			queue1.put(Integer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public  int  getValue(){
		int value= 0;
		try {
			value = queue1.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void main(String[] args) {
		Demo02_QueueAndBlockingComp demo02_ConcurrentLinkedQueue=new Demo02_QueueAndBlockingComp();
		for (int i = 0; i <10 ; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j <5 ; j++) {
						int value= demo02_ConcurrentLinkedQueue.getValue();
						System.out.println("取值："+value);
					}
				}
			}).start();
		}


		for (int i = 0; i <2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j <25 ; j++) {
						demo02_ConcurrentLinkedQueue.setValue(j);
						//System.out.println("放入值："+j);
					}
				}

			}).start();
		}

	/*	try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


	}

}
