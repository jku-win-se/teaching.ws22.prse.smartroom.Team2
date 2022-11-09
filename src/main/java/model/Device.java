package model;

public abstract class Device implements Powerable {

    private final int id;
    private boolean state;

    public Device(int id, boolean state) {
        this.id = id;
        this.state = state;
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

    public boolean isOn() {
        return this.state;
    }

    public void powerOn() {
        if (!this.isOn()) {
            this.togglePower();
        }
    }

    public void powerOff() {
        if (this.isOn()) {
            this.togglePower();
        }
    }

    public void togglePower() {
        this.setState(!this.state);
    }
}