package at.jku.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HumiditySensorRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private HumiditySensor humiditySensor;

    private LocalDateTime timestamp;

    private boolean state;

    private double humidity;

    public HumiditySensorRecord() {
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

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public HumiditySensor getHumiditySensor() {
        return this.humiditySensor;
    }

    public void setHumiditySensor(HumiditySensor humiditySensor) {
        this.humiditySensor = humiditySensor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}