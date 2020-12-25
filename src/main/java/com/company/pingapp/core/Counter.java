package com.company.pingapp.core;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static int nextId() {
        return counter.addAndGet(1);
    }
}
