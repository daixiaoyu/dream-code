package io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class BioServerSingleThread {


    public static void main(String[] args) {
        listen(9999);
    }


    public static void listen(int port) {
        ServerSocket serverSocket = null;
        InputStream in = null;


        try {
            serverSocket = new ServerSocket(port);
            int recvMsgSize = 0;
            byte[] recvBuf = new byte[1024];


            while (true) {

                // 这里是阻塞的 ，必须要处理完这个连接之后才能
                Socket socket = serverSocket.accept();

                TimeUnit.SECONDS.sleep(4);

                System.out.println(" 当前处理接受状态 ");

                SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
                System.out.println(" 处理当前的连接 :" + remoteSocketAddress);

                in = socket.getInputStream();

                while ((recvMsgSize = in.read(recvBuf)) != -1) {
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);

                    System.out.println(new String(temp));
                }
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {

            try {
                serverSocket.close();
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
