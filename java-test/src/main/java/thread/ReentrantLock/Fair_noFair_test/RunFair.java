package thread.ReentrantLock.Fair_noFair_test;

/**
 * 公平与非公平锁: new ReentrantLock(isFair) 默认采用非公平锁 公平锁表示线程获取锁的顺序是按照线程加锁的顺序来分配的，即先来先得的FIFO先进先出顺序。
 */
public class RunFair {

	public static void main(String[] args) throws InterruptedException {
		final Service service = new Service(true); //

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("★线程" + Thread.currentThread().getName()
                                   + "运行了");
				service.serviceMethod();
			}
		};

		Thread[] threadArray = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threadArray[i] = new Thread(runnable);
		}
		for (int i = 0; i < 10; i++) {
			threadArray[i].start();
		}

	}
}