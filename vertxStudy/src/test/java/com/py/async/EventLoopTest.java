package com.py.async;

public class EventLoopTest {

    public static void main(String[] args) {
        EventLoop eventLoop = new EventLoop();
        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                delay(1000);
                eventLoop.dispatch(new EventLoop.Event("tick", i));
            }
            eventLoop.dispatch(new EventLoop.Event("stop", null));
        }).start();
        new Thread(() -> {
            delay(2500);
            eventLoop.dispatch(new EventLoop.Event("hello", "beautiful world"));
            delay(800);
            eventLoop.dispatch(new EventLoop.Event("hello", "beautiful universe"));
        }).start();

        eventLoop.dispatch(new EventLoop.Event("hello", "world"));
        eventLoop.dispatch(new EventLoop.Event("foo", "bar"));

        eventLoop
                .on("hello", s -> System.out.println("hello:" + s))
                .on("tick", n -> System.out.println("tick #" + n))
                .on("stop", n -> eventLoop.stop())
                .run();

        System.out.println("done. Bye!");
    }


    private static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
