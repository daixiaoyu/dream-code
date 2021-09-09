package thread.bfbm.synchronize.Sync;

import thread.bfbm.synchronize.RemoteService;

import java.util.concurrent.TimeUnit;
/**
 * 同步方法只能保证当前方法的原子性
 * 业务的整体原子性，没办法保证
 * 金融行业需要考虑原子性，电商其实并不一定保证脏读问题
 */
public class DirtyRead {

	private String account = "张三";
	private double amount = 100;
	
	public synchronized void setValue(String account, double amount){
		this.account = account;

		RemoteService.handle();
		
		this.amount = amount;
		
		System.out.println("实际结算结果：account = " + account + " , amount = " + amount);
	}
	
	public  void getValue(){
		System.out.println("中间查询结果：account = " + this.account + " , amount = " + this.amount);
	}
	
	
	public static void main(String[] args) throws Exception{
		
		final DirtyRead dr = new DirtyRead();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				dr.setValue("张三", 200);
			}
		});
		t1.start();
		TimeUnit.MILLISECONDS.sleep(1000);
		
		dr.getValue();
	}
	
	
	
}
