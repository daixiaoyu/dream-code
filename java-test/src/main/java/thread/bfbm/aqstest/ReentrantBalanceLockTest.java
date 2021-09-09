/**
 * 公平锁
 */
package thread.bfbm.aqstest;

import java.util.concurrent.locks.ReentrantLock;
/**
 * 巴分巴秒官方交流QQ群:750555573
 * 公平锁
 */
public class ReentrantBalanceLockTest{
	
	public static void main(String[] args) {
		//TestReentrantlock t = new TestReentrantlock();
		TestSync t = new TestSync();
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		t1.start();
		t2.start();
	}
}

class TestReentrantlock extends Thread{
	// 定义一个公平锁
	private static ReentrantLock lock = new ReentrantLock(true);
	public void run(){
		for(int i = 0; i < 5; i++){
			lock.lock();
			try{
			/*	try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				System.out.println(Thread.currentThread().getName() + " get lock");
			}finally{
				lock.unlock();
			}
		}
	}
	
}

class TestSync extends Thread{
	public void run(){
		for(int i = 0; i < 5; i++){
			synchronized (this) {
				System.out.println(Thread.currentThread().getName() + " get lock in TestSync");
			}
		}
	}
}
