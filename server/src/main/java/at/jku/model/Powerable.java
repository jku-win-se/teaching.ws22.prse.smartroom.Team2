package at.jku.model;

public interface Powerable {
    public boolean isOn();
    public void powerOn();
    public void powerOff();
    public void togglePower();
}