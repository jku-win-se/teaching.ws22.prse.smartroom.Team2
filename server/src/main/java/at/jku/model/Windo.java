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
public class Windo implements Openable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonBackReference
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

    public Optional<WindowRecord> getLatestWindowRecord() {
        final Optional<WindowRecord> wr =
                this.windowRecords.stream().max(Comparator.comparing(WindowRecord::getTimestamp));
        return wr;
    }

    public void addWindowRecord(WindowRecord windowRecord) {
        if (windowRecord == null) {
            return;
        }
        this.windowRecords.add(windowRecord);
    }

    @JsonIgnore
    public boolean getState() {
        final Optional<WindowRecord> win =
                this.windowRecords.stream().max(Comparator.comparing(WindowRecord::getTimestamp));
        if (win != null && win.isPresent()) {
            return win.get().getState();
        }
        return false;
    }

    public void setState(boolean state) {
        WindowRecord win = new WindowRecord();
        win.setTimestamp(LocalDateTime.now());
        win.setState(state);
        this.windowRecords.add(win);
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
        if (this.getState()) {
            this.setState(false);
        } else {
            this.setState(true);
        }
    }
}