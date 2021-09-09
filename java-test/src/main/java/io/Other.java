package io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class Other {
    private class ThreadPool {
        List idle = new LinkedList();


        public ThreadPool(int poolSize) {
            for (int i = 0; i < poolSize; i++) {
                WorkThread thread = new WorkThread(this);
                thread.setName("worker" + (i + 1));
                thread.start();
                idle.add(thread);
            }

        }

        public WorkThread getWork() {
            WorkThread thread = null;
            synchronized (idle) {
                if (idle.size() > 0) {
                    thread = (WorkThread) idle.remove(0);

                }
            }
            return thread;
        }

        public void returnWorker(WorkThread workThread) {
            synchronized (idle) {
                idle.add(workThread);
            }
        }

    }

    private class WorkThread extends Thread {
        private ByteBuffer buffer = ByteBuffer.allocate(1024);
        private ThreadPool pool;
        private SelectionKey key;

        public WorkThread(ThreadPool pool) {
            this.pool = pool;
        }

        public synchronized void run() {
            System.out.println(this.getName() + " is ready");
            while (true) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.interrupt();
                }
                if (key == null) {
                    continue;
                }
                System.out.println(this.getName() + " has been awaken");
                try {
                    drainChannel(key);
                } catch (Exception e) {
                    System.out.println("caught '" + e + "' closing channel");
                    try {
                        key.channel().close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    key.selector().wakeup();
                }
                key = null;
                this.pool.returnWorker(this);

            }

        }

        synchronized void serviceChannel(SelectionKey key) {
            this.key = key;
            key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
            this.notify();
        }

        void drainChannel(SelectionKey key) throws Exception {
            SocketChannel channel = (SocketChannel) key.channel();
            buffer.clear();
            int count;
            while ((count = channel.read(buffer)) > 0) {
                buffer.flip();
				/*while(buffer.hasRemaining()){
					channel.write(buffer);
				}*/
                byte[] bytes;
                bytes = new byte[count];
                buffer.get(bytes);
                System.out.println(new String(bytes));
                buffer.clear();
            }
            if (count < 0) {
                channel.close();
                return;
            }
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            key.selector().wakeup();
        }

    }
}
