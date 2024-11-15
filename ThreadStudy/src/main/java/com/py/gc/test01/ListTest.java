package com.py.gc.test01;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public class ListTest {

    static List<Integer> list = new ArrayList<>();
    static WeakHashMap<Object, Integer> wk = new WeakHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            list.add((int) (System.currentTimeMillis() + i));
        }
        ListTest listTest = new ListTest();
        wk.put(listTest, 2);
        int a = list.get(2);
        System.out.println("gc before:" + list.size());
        System.out.println("gc before:");
        wk.forEach((k, v) -> System.out.println(k + ":" + v));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.gc();

        System.out.println("gc after:" + list.size());
        System.out.println("gc after:");
        wk.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
