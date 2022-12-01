package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Door implements Openable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "door_connects_room",
            joinColumns = @JoinColumn(name = "door_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Room> rooms;

    @OneToMany(mappedBy = "door", cascade = CascadeType.ALL)
    private List<DoorRecord> doorRecords;


    public Door() {
        this.doorRecords = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DoorRecord> getDoorRecords() {
        return this.doorRecords;
    }

    public void addDoorRecord(DoorRecord doorRecord) {
        if (doorRecord == null) {
            return;
        }
        this.doorRecords.add(doorRecord);
    }


    @JsonIgnore
    public boolean isOpen() {
        // TODO get state from latest db entry for this door
        return false;
    }

    public void open() {
        if (!this.isOpen()) {
            this.toggle();
        }
    }

    public void close() {
        if (this.isOpen()) {
            this.toggle();
        }
    }

    public void toggle() {
        // TODO state change has to generate a db record
        // if open then close and vice versa
        // create db entry
    }
}