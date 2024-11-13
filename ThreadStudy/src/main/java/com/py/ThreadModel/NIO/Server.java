package com.py.ThreadModel.NIO;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try (ServerSocketChannel sc = ServerSocketChannel.open()) {
            sc.bind(new InetSocketAddress(8060));
            sc.configureBlocking(false);
            while (true) {
                SocketChannel channel = sc.accept();
                if (channel == null) {
                    //没人链接
                    System.out.println("wait conn ...");
                    Thread.sleep(5000);
                } else {
                    System.out.println("conn success");
                }
                if (channel != null) {
                    channel.configureBlocking(false);
                    byteBuffer.flip();
                    int eff = channel.read(byteBuffer);
                    if (eff != 0) {
                        String content = StandardCharsets.UTF_8.decode(byteBuffer).toString();
                        System.out.println(content);
                    } else {
                        System.out.println("no data");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
