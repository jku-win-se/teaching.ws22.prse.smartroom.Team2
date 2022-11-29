package at.jku.model;

import javax.persistence.*;


public abstract class Device implements Powerable {

    private Long id;
    private boolean state;

    public Device() {

    }

    public Device(int id, boolean state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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