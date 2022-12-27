package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PeopleInRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne //(cascade = CascadeType.ALL)
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

    @JsonIgnore
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

    public void setNOPeopleInRoom(int NOPeopleInRoom) {
        this.NOPeopleInRoom = NOPeopleInRoom;
    }
}