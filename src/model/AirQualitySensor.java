package model;

import java.util.concurrent.atomic.AtomicInteger;

public class AirQualitySensor {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private float temperature;
    private float humidity;
    private float co2;

    public AirQualitySensor() {
        this.id = count.incrementAndGet();
        this.temperature = 0.0f;
        this.humidity = 0.0f;
        this.co2 = 0.0f;
    }

    public int getId() {
        return id;
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