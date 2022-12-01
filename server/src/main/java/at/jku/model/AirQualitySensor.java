package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AirQualitySensor implements Powerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "airQualitySensor", cascade = CascadeType.ALL)
    private List<AirQualitySensorRecord> airQualitySensorRecords;

    public AirQualitySensor() {
        this.airQualitySensorRecords = new ArrayList<>();
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

    @JsonIgnore
    public boolean getState() {
        // TODO get state from latest db entry for this device
        return false;
    }

    public void setState(boolean state) {
        // TODO state change has to generate a db record
        // if off then on and vice versa
        // create db entry
    }

    @JsonIgnore
    public boolean isOn() {
        // TODO get state from latest db entry for this device
        return false;
    }

    public void powerOn() {
        if (!this.isOn()) {
            this.togglePower();
        }
    }

    public void powerOff() {
        if (this.isOn()) {
            this.togglePower();
        }
    }

    public void togglePower() {
        // TODO state change has to generate a db record
        // if off then on and vice versa
        // create db entry
    }

    public void setTemperature(double temperature) {
        // TODO create new Record (and therefore db entry) for temperature
        // only all three values at once are allowed!
    }

    public void setHumidity(double humidity) {
        // TODO create new Record (and therefore db entry) for humidity
        // only all three values at once are allowed!
    }

    public void setCo2(double co2) {
        // TODO create new Record (and therefore db entry) for co2
        // only all three values at once are allowed!
    }

    public double getTemperature() {
        // Todo get value of newest record db entry and return
        return 0;
    }

    public double getHumidity() {
        // Todo get value of newest record db entry and return
        return 0;
    }

    public double getCo2() {
        // Todo get value of newest record db entry and return
        return 0;
    }
}