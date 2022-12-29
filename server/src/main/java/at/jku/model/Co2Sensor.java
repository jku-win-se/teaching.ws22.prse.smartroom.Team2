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
public class Co2Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne //(cascade = CascadeType.ALL)
    @JsonBackReference
    private AirQualityDevice airQualityDevice;

    @OneToMany(mappedBy = "co2Sensor", cascade = CascadeType.ALL)
    private List<Co2SensorRecord> co2SensorRecords;

    public Co2Sensor() {
        this.co2SensorRecords = new ArrayList<>();
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

    public List<Co2SensorRecord> getCo2SensorRecords() {
        return co2SensorRecords;
    }

    public void setCo2SensorRecords(List<Co2SensorRecord> co2SensorRecords) {
        this.co2SensorRecords = co2SensorRecords;
    }

    public void addCo2SensorRecord(Co2SensorRecord co2SensorRecord) {
        this.co2SensorRecords.add(co2SensorRecord);
    }

    public void setCo2(double co2) {
        Co2SensorRecord csr = new Co2SensorRecord();
        csr.setTimestamp(LocalDateTime.now());
        csr.setCo2(co2);
        this.co2SensorRecords.add(csr);
    }

    public double getCo2() {
        final Optional<Co2SensorRecord> csr =
                this.co2SensorRecords.stream().max(Comparator.comparing(Co2SensorRecord::getTimestamp));
        return csr.map(Co2SensorRecord::getCo2).orElse(-1.0d);
    }

}