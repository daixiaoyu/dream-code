package thread.synchroniz.dirtyRead;

public class Test {

	public static void main(String[] args) {
		try {
			PublicVar publicVarRef = new PublicVar();

			publicVarRef.getValue();

			ThreadA thread = new ThreadA(publicVarRef);
			thread.start();

			// xxx setValue1
			// xxx setValue2    lock  variable
			// xxx getValuefromsomewhere
			// xxx setValue3



			thread.sleep(200);// 打印结果受此值大小影响

//			publicVarRef.getValue();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}