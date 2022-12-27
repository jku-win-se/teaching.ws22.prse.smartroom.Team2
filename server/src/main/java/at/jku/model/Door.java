package at.jku.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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


    @ManyToMany
    @JoinTable(
            name = "door_connects_room",
            joinColumns = @JoinColumn(name = "door_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
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
        final Optional<DoorRecord> door =
                this.doorRecords.stream().max(Comparator.comparing(DoorRecord::getTimestamp));
        if (door != null && door.isPresent()){
            return door.get().getState();
        }
        return false;
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
        if(this.getState()){
            this.setState(false);
        }else{
            this.setState(true);
        }
    }
}