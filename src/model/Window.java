package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Window {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private boolean state;

    public Window() {
        this.id = count.incrementAndGet();
        this.state = false;
    }

    public int getId() {
        return this.id;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isOpen() {
        return this.state;
    }

    public void toggle() {
        this.setState(!this.state);
    }
}