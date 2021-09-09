package thread;

public class ReentrantLockTest {


    public class Lock {
        private boolean isLocked = false;

        public synchronized void lock() throws InterruptedException {
            while (isLocked) {
                wait();
            }
            isLocked = true;
        }

        public synchronized void unlock() {
            isLocked = false;
            notify();
        }
    }

    public class Count {
        Lock lock = new Lock();

        public void print() throws Exception{
            lock.lock();
            doAdd();
            lock.unlock();
        }

        public void doAdd() throws Exception{
            lock.lock();
            //do something
            lock.unlock();
        }
    }

}
