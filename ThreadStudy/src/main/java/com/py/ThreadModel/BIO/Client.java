package com.py.ThreadModel.BIO;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Client {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        IntStream.of(100).forEach(s->{
            executorService.execute(() -> {
                try (Socket socket = new Socket("127.0.0.1", 8060)) {
                    String msg = "谢谢你呀";
                    for (int i = 0; i < 20; i++) {
                        socket.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

}
