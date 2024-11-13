package com.py.syncdemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class situation01 {

    public static void main(String[] args) {
        BankAccount aa = new BankAccount();
        BankAccount bb = new BankAccount();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 100; i++) {
            queue.add(() -> {
                aa.withdraw(100);
                bb.deposit(100);
            });
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        queue.forEach(executorService::execute);
        System.out.println(aa.getBalance());
        System.out.println(bb.getBalance());
    }
}
