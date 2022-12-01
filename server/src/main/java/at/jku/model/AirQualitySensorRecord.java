package at.jku.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AirQualitySensorRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private AirQualitySensor airQualitySensor;

    private LocalDateTime timestamp;

    private boolean state;

    private double temperature;

    private double humidity;

    private double co2;

    public AirQualitySensorRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }


    public AirQualitySensor getAirQualitySensor() {
        return this.airQualitySensor;
    }

    public void setAirQualitySensor(AirQualitySensor airQualitySensor) {
        this.airQualitySensor = airQualitySensor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}