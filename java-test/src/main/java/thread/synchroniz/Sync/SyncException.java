package thread.synchroniz.Sync;

import java.util.concurrent.TimeUnit;
/**
 * 巴分巴秒官方交流QQ群:750555573
 *
 * @Author:Tony
 * @Create Date:2019/10/05
 * synchronized异常
 *当同步方法中发生异常的时候，自动释放锁资源。不会影响其他线程的执行。
 */
public class SyncException {

	private int i = 0;
	public synchronized void doSomething(){
		while(true){
			try {
				i++;
				TimeUnit.MILLISECONDS.sleep(100);
				if(i <20){
					System.out.println(Thread.currentThread().getName() + " :伤害数=" + i+"--坚持20秒");
				}else{
					System.out.println(Thread.currentThread().getName() + " : 伤害数=" + i+"--切换到我上");
				}

				if(i == 20){
					throw new RuntimeException("出现了异常");
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		final SyncException se = new SyncException();
		System.out.println("开始打怪兽");
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				se.doSomething();
			}
		},"英雄A");
		t1.start();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				se.doSomething();
			}
		},"英雄B");
		t2.start();
	}
	
	
}
