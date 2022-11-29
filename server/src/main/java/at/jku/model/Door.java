package at.jku.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Door extends Gate {
    private static final AtomicInteger count = new AtomicInteger(0);
    public Door(boolean state) {
        super(count.incrementAndGet(), state);
    }
}