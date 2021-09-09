package thread.ReadWriteLock.ReadWriteLockBegin2;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
* @Description 写写互斥
**/
public class Service {

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public void write() {
		try {
			try {
				lock.writeLock().lock();
				System.out.println("获得写锁" + Thread.currentThread().getName()
                                   + " " + System.currentTimeMillis());
				Thread.sleep(1000);
			} finally {
				lock.writeLock().unlock();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}