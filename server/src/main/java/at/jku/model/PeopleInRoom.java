package at.jku.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PeopleInRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Room room;
    private LocalDateTime timestamp;
    private int NOPeopleInRoom;

    public PeopleInRoom() {
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getNOPeopleInRoom() {
        return NOPeopleInRoom;
    }

    public void addNOPeopleInRoom(int NOPeopleInRoom) {
        this.NOPeopleInRoom = NOPeopleInRoom;
    }
}