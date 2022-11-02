package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Ventilator {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private boolean state;

    public Ventilator() {
        this.id = count.incrementAndGet();
        this.state = false;
    }

    public int getId() {
        return id;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isOn() {
        return this.state;
    }

    public void toggle() {
        this.setState(!this.state);
    }
}