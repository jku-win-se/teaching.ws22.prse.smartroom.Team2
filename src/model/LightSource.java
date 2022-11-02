package model;

import java.util.concurrent.atomic.AtomicInteger;

public class LightSource extends Device {
    private static final AtomicInteger count = new AtomicInteger(0);

    public LightSource(boolean state) {
        super(count.incrementAndGet(), state);
    }
}
