package thread.synchroniz.Sync;

import java.util.concurrent.TimeUnit;

/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 * @Author:Tony
 * @Create Date:2019/10/05
 */
public class SyncSubReentry {

	static class Main {
		public int i = 10;
		public synchronized void operationSup(){
			try {
				i--;
				System.out.println("Main print i = " + i);
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Sub extends Main {
		public synchronized void operationSub(){
			try {
				while(i > 0) {
					i--;
					System.out.println("Sub print i = " + i);
					TimeUnit.MILLISECONDS.sleep(100);
					this.operationSup();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				Sub sub = new Sub();
				sub.operationSub();
			}
		});
		
		t1.start();
	}
	
	
}
