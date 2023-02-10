package at.jku.model;

/**
 * The `Powerable` interface represents the AirQualityDevices, Lights and Ventilators that can be turned on/off.
 * */

public interface Powerable {
    public boolean isOn();
    public void powerOn();
    public void powerOff();
    public void togglePower();
}