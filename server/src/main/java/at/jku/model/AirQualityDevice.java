package at.jku.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class AirQualityDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonBackReference
    private Room room;

    @OneToOne(cascade = CascadeType.ALL)
    private Co2Sensor co2Sensor;
    @OneToOne(cascade = CascadeType.ALL)
    private TemperatureSensor temperatureSensor;
    @OneToOne(cascade = CascadeType.ALL)
    private HumiditySensor humiditySensor;

    public AirQualityDevice() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}