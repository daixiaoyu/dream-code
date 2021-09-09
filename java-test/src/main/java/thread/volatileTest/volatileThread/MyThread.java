package thread.volatileTest.volatileThread;

public class MyThread extends Thread {
//	volatile不具备原子性
	volatile public static int count;

	private static void addCount() {
		for (int i = 0; i < 100; i++) {
			count++;
		}
		System.out.println("count=" + count);
	}

	@Override
	public void run() {
		addCount();
	}

}