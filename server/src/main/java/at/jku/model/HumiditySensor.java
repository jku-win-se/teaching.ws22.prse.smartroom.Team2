package at.jku.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
public class HumiditySensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne //(cascade = CascadeType.ALL)
    @JsonBackReference
    private AirQualityDevice airQualityDevice;

    @OneToMany(mappedBy = "humiditySensor", cascade = CascadeType.ALL)
    private List<HumiditySensorRecord> humiditySensorRecords;

    public HumiditySensor() {
        this.humiditySensorRecords = new ArrayList<>();
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

    public void addHumiditySensorRecord(HumiditySensorRecord humiditySensorRecord) {
        this.humiditySensorRecords.add(humiditySensorRecord);
    }

    public void setHumidity(double humidity) {
        HumiditySensorRecord hsr = new HumiditySensorRecord();
        hsr.setTimestamp(LocalDateTime.now());
        hsr.setHumidity(humidity);
        this.humiditySensorRecords.add(hsr);
    }

    public double getHumidity() {
        final Optional<HumiditySensorRecord> hsr =
                this.humiditySensorRecords.stream().max(Comparator.comparing(HumiditySensorRecord::getTimestamp));
        return hsr.map(HumiditySensorRecord::getHumidity).orElse(-1.0d);
    }

    public Optional<HumiditySensorRecord> getLatestHumiditySensorRecord() {
        return this.humiditySensorRecords.stream().max(Comparator.comparing(HumiditySensorRecord::getTimestamp));
    }

}