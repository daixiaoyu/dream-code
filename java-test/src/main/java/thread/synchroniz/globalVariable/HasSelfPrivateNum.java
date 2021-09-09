package thread.synchroniz.globalVariable;

/**
 * 在并发的情况下操作全局变量可能发生线程不安全问题
 */
public class HasSelfPrivateNum {

	private volatile int num = 0;

//	synchronized   jvm lock
	public synchronized void addI(String username) {  // monitorenter  enter monitor room
		try {  //可见性
			if (username.equals("a")) {
				num = 100;
				System.out.println("a set over!");
				Thread.sleep(2000);
			} else {
				num = 200;
				System.out.println("b set over!");
			}
			System.out.println(username + " num=" + num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}  // monitorexit

}