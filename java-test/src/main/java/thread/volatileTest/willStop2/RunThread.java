package thread.volatileTest.willStop2;

public class RunThread extends Thread {
//	使用volatile修饰的变量，在多线程中共享变量，来控制循环是否继续执行
	private volatile boolean isRunning = true;  // 实例变量  堆  heap  共享主存

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public void run() {
		System.out.println("进入run了");
		while (isRunning == true) {
		}
		System.out.println("线程被停止了！");
	}


}