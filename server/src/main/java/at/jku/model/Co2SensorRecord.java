package at.jku.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Co2SensorRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Co2Sensor co2Sensor;

    private LocalDateTime timestamp;

    private boolean state;

    private double co2;

    public Co2SensorRecord() {
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

    public double getCo2() {
        return this.co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public Co2Sensor getCo2Sensor() {
        return this.co2Sensor;
    }

    public void setCo2Sensor(Co2Sensor co2Sensor) {
        this.co2Sensor = co2Sensor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}