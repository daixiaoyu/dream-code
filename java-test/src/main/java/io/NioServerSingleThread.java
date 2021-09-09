package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;


/**
 *
 */
public class NioServerSingleThread {

    private static final int BUF_SIZE = 1024;

    public static void main(String[] args) {
        NioServerSingleThread nioServer = new NioServerSingleThread();
        nioServer.listen(9999);
    }


    public void listen(int port) {
        Selector selector = null;
        ServerSocketChannel socketChannel = null;

        try {
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            socketChannel.socket().bind(new InetSocketAddress(port));

            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {

                if (selector.select(3000) == 0) {
                    System.out.println("开始轮询");
                    continue;
                }

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();
                    if (key.isConnectable()) {
                        System.out.println("已连接");
                    }

                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }

                    if (key.isReadable()) {
                        System.out.println("可读");
                        handleRead(key);
                    }

                    if (key.isWritable() && key.isValid()) {
                        System.out.println("可写");
                        handleWrite(key);
                    }

                    iterator.remove();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void handleAccept(SelectionKey selectionKey) throws Exception {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = channel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }

    public void handleRead(SelectionKey selectionKey) throws Exception {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = (ByteBuffer) selectionKey.attachment();
        long bytesRead = socketChannel.read(buf);
        while (bytesRead > 0) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = socketChannel.read(buf);
        }
        if (bytesRead == -1) {
            socketChannel.close();
        }
    }

    public  void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while (buf.hasRemaining()) {
            sc.write(buf);
        }
        buf.compact();
    }

}
