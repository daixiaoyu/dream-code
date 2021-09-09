package thread.ReadWriteLock.ReadWriteLockBegin4;

public class Run {

	public static void main(String[] args) throws InterruptedException {

		Service service = new Service();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

		Thread.sleep(1000);


		ThreadA a1 = new ThreadA(service);
		a1.setName("A1");
		a1.start();

		ThreadB b1 = new ThreadB(service);
		b1.setName("B1");
		b1.start();


	}

}