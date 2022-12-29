package at.jku.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
public class TemperatureSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne //(cascade = CascadeType.ALL)
    @JsonBackReference
    private AirQualityDevice airQualityDevice;

    @OneToMany(mappedBy = "temperatureSensor", cascade = CascadeType.ALL)
    private List<TemperatureSensorRecord> temperatureSensorRecords;

    public TemperatureSensor() {
        this.temperatureSensorRecords = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AirQualityDevice getAirQualityDevice() {
        return this.airQualityDevice;
    }

    public void setAirQualityDevice(AirQualityDevice airQualityDevice) {
        this.airQualityDevice = airQualityDevice;
    }

    public void addTemperatureSensorRecord(TemperatureSensorRecord temperatureSensorRecord) {
        this.temperatureSensorRecords.add(temperatureSensorRecord);
    }

    public void setTemperature(double temperature) {
        TemperatureSensorRecord tsr = new TemperatureSensorRecord();
        tsr.setTimestamp(LocalDateTime.now());
        tsr.setTemperature(temperature);
        this.temperatureSensorRecords.add(tsr);
    }

    public double getTemperature() {
        final Optional<TemperatureSensorRecord> tsr =
                this.temperatureSensorRecords.stream().max(Comparator.comparing(TemperatureSensorRecord::getTimestamp));
        return tsr.map(TemperatureSensorRecord::getTemperature).orElse(-1.0d);
    }
}