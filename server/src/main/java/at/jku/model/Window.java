package at.jku.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Window extends Gate {

    private static final AtomicInteger count = new AtomicInteger(0);

    public Window(boolean state) {
        super(count.incrementAndGet(), state);
    }
}