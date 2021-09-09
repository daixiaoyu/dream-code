package thread.synchroniz.synchronizedUpdateNewValue;

/**
 * synchronized可以使多个线程访问同一个资源具有同步性，而且它还具有将线程工作内存中的私有变量与公共内存中的变量同步的功能。
 * 多线程并发着重“外练互斥，内修可见”，这是多线程并发的重要技术点。
 */
public class Service {

	private boolean isContinueRun = true;  // heap  shared  false

	public void runMethod() {
		String anyString = new String();
		while (isContinueRun == true) {
			//synchronized使isContinueRun变成了线程共享的变量
			synchronized (anyString){

			}
		}
		System.out.println("停下来了！");
	}

	public void stopMethod() {
		isContinueRun = false;
	}
}