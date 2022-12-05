package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HumiditySensor implements Powerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

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

    public void setHumidity(double humidity) {
        // TODO create new Record (and therefore db entry) for temperature
    }

    public double getHumidity() {
        // Todo get value of newest record db entry and return
        return 0;
    }
}