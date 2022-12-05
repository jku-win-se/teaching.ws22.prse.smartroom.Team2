package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Windo implements Openable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "window", cascade = CascadeType.ALL)
    private List<WindowRecord> windowRecords;

    public Windo() {
        this.windowRecords = new ArrayList<>();
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

    public List<WindowRecord> getWindowRecords() {
        return this.windowRecords;
    }

    public void addWindowRecord(WindowRecord windowRecord) {
        if (windowRecord == null) {
            return;
        }
        this.windowRecords.add(windowRecord);
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