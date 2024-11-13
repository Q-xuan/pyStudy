package com.py.ThreadModel.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        byte[] buffer = new byte[1024];

        ExecutorService executorService = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(8060);) {
            System.out.println("server listen 8060");
            for (; ; ) {
                System.out.println();
                System.out.println("服务器正在等待连接...");
                Socket socket = serverSocket.accept();
                executorService.execute(() -> {
                    System.out.println("id:" + Thread.currentThread().getName() + ",服务器已接收到连接请求...");
                    System.out.println();
                    System.out.println("服务器正在等待数据...");
                    try {
                        socket.getInputStream().read(buffer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("服务器已经接收到数据");
                    System.out.println();
                    String content = new String(buffer);
                    System.out.println("接收到的数据:" + content);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
