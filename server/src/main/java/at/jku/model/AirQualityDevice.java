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
public class AirQualityDevice implements Powerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonBackReference
    private Room room;
    @OneToMany(mappedBy = "airQualityDevice", cascade = CascadeType.ALL)
    private List<AirQualityDeviceRecord> airQualityDeviceRecords;
    @OneToOne(cascade = CascadeType.ALL)
    private Co2Sensor co2Sensor;
    @OneToOne(cascade = CascadeType.ALL)
    private TemperatureSensor temperatureSensor;
    @OneToOne(cascade = CascadeType.ALL)
    private HumiditySensor humiditySensor;

    public AirQualityDevice() {
        this.airQualityDeviceRecords = new ArrayList<>();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean getState() {
        final Optional<AirQualityDeviceRecord> asr =
                this.airQualityDeviceRecords.stream().max(Comparator.comparing(AirQualityDeviceRecord::getTimestamp));
        return asr.map(AirQualityDeviceRecord::getState).orElse(false);
    }

    public void setState(boolean state) {
        AirQualityDeviceRecord asr = new AirQualityDeviceRecord();
        asr.setTimestamp(LocalDateTime.now());
        asr.setState(state);
        this.airQualityDeviceRecords.add(asr);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Co2Sensor getCo2Sensor() {
        return co2Sensor;
    }

    public void setCo2Sensor(Co2Sensor co2Sensor) {
        this.co2Sensor = co2Sensor;
    }

    public TemperatureSensor getTemperatureSensor() {
        return temperatureSensor;
    }

    public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    public HumiditySensor getHumiditySensor() {
        return humiditySensor;
    }

    public void setHumiditySensor(HumiditySensor humiditySensor) {
        this.humiditySensor = humiditySensor;
    }

    public double getHumidity() {
        return this.humiditySensor != null ? humiditySensor.getHumidity() : -1;
    }

    public double getTemperature() {
        return this.temperatureSensor != null ? this.temperatureSensor.getTemperature() : -273.15;
    }

    public double getCo2() {
        return this.co2Sensor != null ? this.co2Sensor.getCo2() : -273.15;
    }

    public List<AirQualityDeviceRecord> getAirQualityDeviceRecords() {
        return airQualityDeviceRecords;
    }

    public void setAirQualityDeviceRecords(List<AirQualityDeviceRecord> airQualityDeviceRecords) {
        this.airQualityDeviceRecords = airQualityDeviceRecords;
    }

    @Override
    public boolean isOn() {
        return this.getState();
    }

    @Override
    public void powerOn() {
        if (!this.isOn()) {
            this.togglePower();
        }
    }

    @Override
    public void powerOff() {
        if (this.isOn()) {
            this.togglePower();
        }
    }

    @Override
    public void togglePower() {
        this.setState(!this.getState());
    }

    public Optional<AirQualityDeviceRecord> getLatestAirQualityDeviceRecord() {
        return this.airQualityDeviceRecords.stream().max(Comparator.comparing(AirQualityDeviceRecord::getTimestamp));
    }
}