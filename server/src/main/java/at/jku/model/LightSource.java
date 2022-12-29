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
public class LightSource implements Powerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonBackReference
    private Room room;

    public void setHex(String hex) {
        this.hex = hex;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    private String hex; //color

    private int brightness;

    @OneToMany(mappedBy = "lightSource", cascade = CascadeType.ALL)
    private List<LightSourceRecord> lightSourceRecords;

    public LightSource() {
        this.lightSourceRecords = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getHex() {
        return this.hex;
    }

    public int getBrightness() {
        return this.brightness;
    }

    @JsonIgnore
    public boolean getState() {
        final Optional<LightSourceRecord> lsr =
                this.lightSourceRecords.stream().max(Comparator.comparing(LightSourceRecord::getTimestamp));
        return lsr.map(LightSourceRecord::getState).orElse(false);
    }

    public void setState(boolean state) {
        LightSourceRecord lsr = new LightSourceRecord();
        lsr.setTimestamp(LocalDateTime.now());
        lsr.setState(state);
        this.lightSourceRecords.add(lsr);
    }

    @JsonIgnore
    public List<LightSourceRecord> getLightSourceRecords() {
        return this.lightSourceRecords;
    }

    public void addLightSourceRecord(LightSourceRecord lightSourceRecord) {
        if (lightSourceRecord == null) {
            return;
        }
        this.lightSourceRecords.add(lightSourceRecord);
    }

    public boolean isOn() {
        return this.getState();
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
        this.setState(!this.getState());
    }
}
