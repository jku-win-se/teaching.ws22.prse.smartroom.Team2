package at.jku.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private int size;
    private int numPeopleInRoom;
//     private List<Door> doors;
//     private List<Window> windows;
//     private List<Ventilator> ventilators;
//     private List<AirQualityDevice> airQualitySensors;


    public Room() {
    }

    public Room(String name, int size) {
        this.name = name;
        this.size = size;
//         this.doors = new ArrayList<>();
//         this.windows = new ArrayList<>();
//         this.ventilators = new ArrayList<>();
//         this.airQualitySensors = new ArrayList<>();
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNOPeopleInRoom() {
        return numPeopleInRoom;
    }

    public void setNOPeopleInRoom(int numPeopleInRoom) {
        this.numPeopleInRoom = numPeopleInRoom;
    }

//    public List<Door> getDoors() {
//        return doors;
//    }

//    public List<Window> getWindows() {
//        return windows;
//    }

//    public List<Ventilator> getVentilators() {
//        return ventilators;
//    }

//    public List<AirQualityDevice> getAirQualitySensors() {
//        return airQualitySensors;
//    }
}