package model;

import java.util.concurrent.atomic.AtomicInteger;

public class AirQualityDevice extends Device {
    private static final AtomicInteger count = new AtomicInteger(0);
    private float temperature;
    private float humidity;
    private float co2;

    public AirQualityDevice(boolean state, float temperature, float humidity, float co2) {
        super(count.incrementAndGet(), state);
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getCo2() {
        return co2;
    }
}