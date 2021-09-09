package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *
 */
public class NioClient {

    static BlockingQueue workQueue = new ArrayBlockingQueue(500);
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8, 60, TimeUnit.SECONDS, workQueue);

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {

            int time = i * 50;
            SendThread send = new SendThread(time);
            threadPoolExecutor.execute(send);
        }

    }


    public static class SendThread implements Runnable {

        private int time;

        public SendThread(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = null;

            try {
                socketChannel = socketChannel.open();
                socketChannel.configureBlocking(false);
                socketChannel.connect(new InetSocketAddress("localhost", 9999));

                if (socketChannel.finishConnect()) {

                    for (int time = 0; time < time; time++) {

                        TimeUnit.SECONDS.sleep(1);

                        Thread thread = Thread.currentThread();
                        String name = thread.getName();

                        String info = " 传送 " + time + "，来自客户端:" + name;

                        byteBuffer.clear();
                        byteBuffer.put(info.getBytes());
                        byteBuffer.flip();

                        while (byteBuffer.hasRemaining()) {
                            System.out.println(byteBuffer);
                            socketChannel.write(byteBuffer);
                        }
                    }

                }

            } catch (IOException | InterruptedException exception) {
                exception.fillInStackTrace();
            } finally {
                if (socketChannel != null) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
