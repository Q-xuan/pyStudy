package com.py.thread.volatiletest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author py
 * @Date 2024/12/4
 */
public class ThreadStopExample {

    private static boolean stop;

    public static void main(String[] args) throws InterruptedException {
        Thread workThread = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (!stop) {
                    i++;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workThread.start();
        TimeUnit.SECONDS.sleep(3);
        stop = true;
    }
}
