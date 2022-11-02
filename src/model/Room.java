package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private int size;
    private int NOPeopleInRoom;
    private List<Door> doors;
    private List<Window> windows;
    private List<Ventilator> ventilators;
    private List<AirQualityDevice> airQualitySensors;

    public Room(String name, int size, int numPeopleInRoom) {
        this.name = name;
        this.size = size;
        this.NOPeopleInRoom = numPeopleInRoom;
        this.doors = new ArrayList<>();
        this.windows = new ArrayList<>();
        this.ventilators = new ArrayList<>();
        this.airQualitySensors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getNOPeopleInRoom() {
        return NOPeopleInRoom;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public List<Window> getWindows() {
        return windows;
    }

    public List<Ventilator> getVentilators() {
        return ventilators;
    }

    public List<AirQualityDevice> getAirQualitySensors() {
        return airQualitySensors;
    }
}