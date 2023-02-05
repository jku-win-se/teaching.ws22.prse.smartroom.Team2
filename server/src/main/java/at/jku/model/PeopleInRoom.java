package at.jku.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PeopleInRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JsonBackReference
    private Room room;
    private LocalDateTime timestamp;
    private int numPeopleInRoom;

    public PeopleInRoom() {
        // nothing needed @SonarLint
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

    public int getNumPeopleInRoom() {
        return numPeopleInRoom;
    }

    public void setNumPeopleInRoom(int numPeopleInRoom) {
        this.numPeopleInRoom = numPeopleInRoom;
    }
}