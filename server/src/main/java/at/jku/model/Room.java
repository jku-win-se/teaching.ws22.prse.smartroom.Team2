package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private int size;
    @ManyToMany
    @JoinTable(
            name = "door_connects_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "door_id"))
    private Set<Door> doors;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Windo> windows;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Ventilator> ventilators;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AirQualitySensor> airQualitySensors;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LightSource> lightSources;

    public Room() {
        this.doors = new HashSet<>();
        this.windows = new HashSet<>();
        this.ventilators = new HashSet<>();
        this.airQualitySensors = new HashSet<>();
        this.lightSources = new HashSet<>();
    }

    public Room(String name) {
        this.name = name;
        this.doors = new HashSet<>();
        this.windows = new HashSet<>();
        this.ventilators = new HashSet<>();
        this.airQualitySensors = new HashSet<>();
        this.lightSources = new HashSet<>();
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

    public int getNumPeopleInRoom() {
        // Get newest db entry from people_in_room table
        return 0;
    }

    public void setNumPeopleInRoom(int numPeopleInRoom) {
        // TODO Generate DB Entry in people_in_room table
    }

    public Set<Door> getDoors() {
        return doors;
    }

    public void addDoor(Door door) {
        if (door == null) {
            return;
        }
        this.doors.add(door);
    }

    public Set<Windo> getWindows() {
        return windows;
    }

    public void addWindow(Windo window) {
        if (window == null) {
            return;
        }
        this.windows.add(window);
    }

    public Set<Ventilator> getVentilators() {
        return ventilators;
    }

    public void addVentilator(Ventilator ventilator) {
        if (ventilator == null) {
            return;
        }
        this.ventilators.add(ventilator);
    }

    public Set<AirQualitySensor> getAirQualitySensors() {
        return airQualitySensors;
    }

    public void addAirQualitySensor(AirQualitySensor airQualitySensor) {
        if (airQualitySensor == null) {
            return;
        }
        this.airQualitySensors.add(airQualitySensor);
    }

    public Set<LightSource> getLightSources() {
        return lightSources;
    }

    public void addLightSource(LightSource lightSource) {
        if (lightSource == null) {
            return;
        }
        this.lightSources.add(lightSource);
    }


    public double getTemperature() {
        return airQualitySensors.stream()
                .mapToDouble(AirQualitySensor::getTemperature)
                .average().orElse(-273.15d);

    }

    public double getHumidity() {
        return airQualitySensors.stream()
                .mapToDouble(AirQualitySensor::getHumidity)
                .average().orElse(-1.0d);
    }

    public double getCo2() {
        return airQualitySensors.stream()
                .mapToDouble(AirQualitySensor::getCo2)
                .average().orElse(-1.0d);
    }
}