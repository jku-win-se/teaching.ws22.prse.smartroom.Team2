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
public class Ventilator implements Powerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonBackReference
    private Room room;

    @OneToMany(mappedBy = "ventilator", cascade = CascadeType.ALL)
    private List<VentilatorRecord> ventilatorRecords;

    public Ventilator() {
        this.ventilatorRecords = new ArrayList<>();
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


    public List<VentilatorRecord> getVentilatorRecords() {
        return this.ventilatorRecords;
    }

    public void addVentilatorRecord(VentilatorRecord ventilatorRecord) {
        if (ventilatorRecord == null) {
            return;
        }
        this.ventilatorRecords.add(ventilatorRecord);
    }

    @JsonIgnore
    public boolean getState() {
        final Optional<VentilatorRecord> vr =
                this.ventilatorRecords.stream().max(Comparator.comparing(VentilatorRecord::getTimestamp));
        if (vr != null && vr.isPresent()) {
            return vr.get().getState();
        }
        return false;
    }

    public void setState(boolean state) {
        VentilatorRecord vr = new VentilatorRecord();
        vr.setTimestamp(LocalDateTime.now());
        vr.setState(state);
        this.ventilatorRecords.add(vr);
    }

    @JsonIgnore
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
        if (this.getState()) {
            this.setState(false);
        } else {
            this.setState(true);
        }
    }
}