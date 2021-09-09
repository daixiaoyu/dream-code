
package thread.bfbm.container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
/**
 *  巴分巴秒官方交流QQ群:750555573
 *
 */
public class Demo02_ConcurrentLinkedQueue {
	private Queue<Integer> queue = new ConcurrentLinkedQueue<>();
	public  void  setValue(int value){
		queue.offer(value);
	}

	public  int  getValue(){
		//@return the head of this queue, or {@code null} if this queue is empty
	/*	Integer value=queue.poll();
		if(value==null){
			value=-1;
		}*/
		// This method differs
		//     * from {@link #poll poll} only in that it throws an exception if this
		//     * queue is empty.
		int value=queue.remove();
		//System.out.println("出队后，数量："+queue.size());
		return value;
	}
	
	public static void main(String[] args) {
		Demo02_ConcurrentLinkedQueue demo02_ConcurrentLinkedQueue=new Demo02_ConcurrentLinkedQueue();
		/*for (int i = 0; i <2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j <25 ; j++) {
						demo02_ConcurrentLinkedQueue.setValue(j);
						//System.out.println("放入值："+j);
					}
				}

			}).start();
		}*/

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	}

}
