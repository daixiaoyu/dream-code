package thread.synchroniz.Sync;
/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 * @Author:Tony
 * @Create Date:2019/10/05
 */
public class SyncReentry {

	public synchronized void doSomething1(){
		System.out.println("doSomething1..");
		doSomething2();
	}
	public synchronized void doSomething2(){
		System.out.println("doSomething2..");
		doSomething3();
	}
	public synchronized void doSomething3(){
		System.out.println("doSomething3..");
	}
	
	public static void main(String[] args) {
		final SyncReentry sr = new SyncReentry();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				sr.doSomething1();
			}
		});
		t1.start();
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				sr.doSomething2();
			}
		});
		t2.start();
	}
}
