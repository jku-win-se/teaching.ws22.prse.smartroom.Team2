package at.jku.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AirQualityDeviceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private AirQualityDevice airQualityDevice;

    private LocalDateTime timestamp;

    private boolean state;

    public AirQualityDeviceRecord() {
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

    public AirQualityDevice getAirQualityDevice() {
        return this.airQualityDevice;
    }

    public void setAirQualityDevice(AirQualityDevice airQualityDevice) {
        this.airQualityDevice = airQualityDevice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}