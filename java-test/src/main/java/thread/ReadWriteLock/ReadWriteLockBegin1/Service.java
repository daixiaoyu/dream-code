package thread.ReadWriteLock.ReadWriteLockBegin1;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读读不互斥
 */
public class Service {

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public void read() {
		try {
			try {
				lock.readLock().lock();
				System.out.println("获得读锁" + Thread.currentThread().getName()
                                   + " " + System.currentTimeMillis());
				Thread.sleep(1000);
			} finally {
				lock.readLock().unlock();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}