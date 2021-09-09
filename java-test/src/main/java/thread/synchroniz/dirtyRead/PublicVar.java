package thread.synchroniz.dirtyRead;

public class PublicVar {

	public String username = "A";
	public String password = "AA";

	synchronized public void setValue(String username, String password) {
		try {
			this.username = username;
			Thread.sleep(1000);
			this.password = password;

			System.out.println("setValue method synchronize name="
                               + Thread.currentThread().getName() + " username="
                               + username + " password=" + password);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//加上synchronized则不会出现脏读
	public synchronized void getValue() {
		System.out.println("getValue method synchronize name="
                           + Thread.currentThread().getName() + " username=" + username
                           + " password=" + password);
	}

}