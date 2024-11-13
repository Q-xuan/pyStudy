package com.py.thread01;

import java.util.concurrent.*;

public class EasyThread extends Thread implements Runnable, Callable<String> {
    @Override
    public void run() {
        System.out.println("hello thread");
    }

    @Override
    public void start() {
        System.out.println("hello start");
    }

    @Override
    public String call() {
        return "hello call";
    }

    /**
     * 简单的线程创建方式
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //第一种
        new Thread(new EasyThread()).start();
        //第二种
        new Thread(() -> System.out.println("hello lambda")).start();
        //第三种
        new EasyThread().start();
        //第四种
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(() -> System.out.println("hello executors"));
        //第五种
        String s = pool.submit((Callable<String>) new EasyThread()).get();
        System.out.println(s);
        //第六种
        ForkJoinTask<?> task1 = RecursiveTask.adapt(() -> System.out.println("hello forkJoinPool 1"));
        ForkJoinTask<?> task2 = RecursiveTask.adapt(() -> System.out.println("hello forkJoinPool 2"));
        ForkJoinTask<?> fork = task1.fork();
        ForkJoinTask<?> fork1 = task2.fork();
        fork.get();
        fork1.get();
        //第七种
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> "hello futureTask");
        pool.execute(stringFutureTask);
        String s1 = stringFutureTask.get();
        System.out.println(s1);
    }
}
