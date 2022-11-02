package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Ventilator extends Sensor {
    private static final AtomicInteger count = new AtomicInteger(0);

    public Ventilator(boolean state) {
        super(count.incrementAndGet(), state);
    }
}