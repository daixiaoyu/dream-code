package io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BioServerThreadPool {

    private BlockingQueue workQueue = new ArrayBlockingQueue(1);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, workQueue);

    private BioServerThreadPool() {

    }

    public static void main(String[] args) {
        BioServerThreadPool bioServer = new BioServerThreadPool();
        bioServer.listen(9999);
    }

    public void listen(int port) {
        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(port);


            while (true) {

                // 阻塞
                Socket socket = serverSocket.accept();

                System.out.println(" 当前处理接受状态 ");

                //为了能够处理其他的客户端连接，这里异步去处理当前连接
                //优点：简单易懂
                //缺点: 这样使用 只能一个线程处理一个连接，则如果连接过多，则需要频繁的切换上下文，吞吐量会降低
                threadPoolExecutor.execute(new HandlerThread(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    class HandlerThread implements Runnable {

        private Socket socket;

        public HandlerThread(Socket socketIn) {
            socket = socketIn;
        }

        @Override
        public void run() {
            SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
            InputStream in = null;
            try {
                in = socket.getInputStream();

                int recvMsgSize = 0;
                byte[] recvBuf = new byte[1024];

                while (((recvMsgSize = in.read(recvBuf)) != -1)) {
                    Thread thread = Thread.currentThread();

                    System.out.println(" 当前线程 :" + thread.getName());
                    System.out.println(" 处理当前的连接 :" + remoteSocketAddress);

                    byte[] temp = new byte[recvMsgSize];

                    System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);

                    System.out.println(new String(temp));
                }

            } catch (Exception e) {

                e.printStackTrace();

            } finally {
                try {
                    socket.close();
                    if (null != in) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
