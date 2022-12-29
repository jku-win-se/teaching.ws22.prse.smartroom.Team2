package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Door implements Openable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "doors")
    private Set<Room> rooms = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Set<Room> getRooms(Room room) {
        return rooms;
    }

    public void deleteRoom(Room room) {
        rooms.remove(room);
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
    public boolean getState() {
        final Optional<DoorRecord> dr =
                this.doorRecords.stream().max(Comparator.comparing(DoorRecord::getTimestamp));
        return dr.map(DoorRecord::getState).orElse(false);
    }

    public Optional<DoorRecord> getLatestDoorRecord() {
        return this.doorRecords.stream().max(Comparator.comparing(DoorRecord::getTimestamp));
    }

    public void setState(boolean state) {
        DoorRecord door = new DoorRecord();
        door.setTimestamp(LocalDateTime.now());
        door.setState(state);
        this.doorRecords.add(door);
    }

    @JsonIgnore
    public boolean isOpen() {
        return this.getState();
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
        this.setState(!this.getState());
    }
}