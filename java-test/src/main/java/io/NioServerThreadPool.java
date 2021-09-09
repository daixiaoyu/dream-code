package io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class NioServerThreadPool {

    private BlockingQueue workQueue = new ArrayBlockingQueue(50);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, workQueue);


    public static void main(String[] args) throws Exception {
        new NioServerThreadPool().listen(9999);
    }

    public void listen(int port) throws Exception {
        System.out.println("Listenning on port:" + port);
        // 创建通道 ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定监听端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // 设置为非阻塞方式
        serverSocketChannel.configureBlocking(false);
        // 创建选择器
        Selector selector = Selector.open();

        // 通道注册到选择器
        // 当前channel 注册 接受请求的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            TimeUnit.SECONDS.sleep(1);

            // 一直阻塞，直到有数据请求
            int n = selector.select();
            if (n == 0) {
                continue;
            }

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    System.out.println("监听到一个可连接事件");
                    // 获取到当前的channel
                    // 并在selector 上注册可以 读数据的事件
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socket = server.accept();
                    registerChannel(selector, socket, SelectionKey.OP_READ);
                    sayHello(socket);
                }

                // 当监听到 可以读数据 的时候
                if (key.isReadable()) {
                    System.out.println("监听到一个可读事件");
                    // 监听到可以读的连接
                    readDataFromSocket(key);
                }
                it.remove();
            }

        }

    }

    public void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    public void sayHello(SocketChannel socket) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        buffer.put("连接成功\n".getBytes());
        buffer.flip();
        socket.write(buffer);
    }


    public void readDataFromSocket(SelectionKey key) throws Exception {
        threadPoolExecutor.execute(new ReadThread(key));
    }


    class ReadThread implements Runnable {
        private SelectionKey selectionKey;

        public ReadThread(SelectionKey key) {
            this.selectionKey = key;
        }

        private ByteBuffer buffer = ByteBuffer.allocate(1024);

        private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

        @Override
        public void run() {
            try {

                Thread thread = Thread.currentThread();

                System.out.println("当前处理数据的线程:" + thread.getName());


                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                if (null == socketChannel) {
                    return;
                }

                long bytesRead = socketChannel.read(buffer);

                while (bytesRead > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        System.out.print((char) buffer.get());
                    }
                    System.out.println();
                    buffer.clear();
                    bytesRead = socketChannel.read(buffer);
                }

                if (bytesRead == -1) {
                    socketChannel.close();
                }

                String message = "已收到\n";

                writeBuffer.clear();
                writeBuffer.put(message.getBytes());
                writeBuffer.flip();
                socketChannel.write(writeBuffer);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
