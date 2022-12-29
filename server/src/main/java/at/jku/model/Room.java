package at.jku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private int size;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "door_connects_room",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "door_id", referencedColumnName = "id"))
    private Set<Door> doors;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Windo> windows;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Ventilator> ventilators;
    /*
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<TemperatureSensor> temperatureSensors;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Co2Sensor> co2Sensors;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<HumiditySensor> humiditySensors;
    */
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<LightSource> lightSources;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<PeopleInRoom> peopleInRooms;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<AirQualityDevice> airQualityDevices;

    public Room() {
        this.doors = new HashSet<>();
        this.windows = new HashSet<>();
        this.ventilators = new HashSet<>();
        /*
        this.temperatureSensors = new HashSet<>();
        this.co2Sensors = new HashSet<>();
        this.humiditySensors = new HashSet<>();
        */
        this.airQualityDevices = new HashSet<>();
        this.lightSources = new HashSet<>();
        this.peopleInRooms = new ArrayList<>();
    }

    public Room(String name) {
        this.name = name;
        this.doors = new HashSet<>();
        this.windows = new HashSet<>();
        this.ventilators = new HashSet<>();
        /*
        this.temperatureSensors = new HashSet<>();
        this.co2Sensors = new HashSet<>();
        this.humiditySensors = new HashSet<>();
         */
        this.airQualityDevices = new HashSet<>();
        this.lightSources = new HashSet<>();
        this.peopleInRooms = new ArrayList<>();
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

    public PeopleInRoom getNumPeopleInRoom() {
        final Optional<PeopleInRoom> pir =
                this.peopleInRooms.stream()
                        .max(Comparator.comparing(PeopleInRoom::getTimestamp));
        return pir.isPresent() ? pir.get() : null;
    }

    public void addPeopleInRoom(PeopleInRoom peopleInRoom) {
        if (peopleInRoom == null) {
            return;
        }
        this.peopleInRooms.add(peopleInRoom);
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

    /*
    public Set<HumiditySensor> getHumiditySensors() {
        return this.humiditySensors;
    }

    public void addHumiditySensor(HumiditySensor humiditySensor) {
        if (humiditySensor == null) {
            return;
        }
        this.humiditySensors.add(humiditySensor);
    }

    public Set<Co2Sensor> getCo2Sensors() {
        return this.co2Sensors;
    }

    public void addCo2Sensor(Co2Sensor co2Sensor) {
        if (co2Sensor == null) {
            return;
        }
        this.co2Sensors.add(co2Sensor);
    }

    public Set<TemperatureSensor> getTemperatureSensors() {
        return temperatureSensors;
    }

    public void addTemperatureSensor(TemperatureSensor temperatureSensor) {
        if (temperatureSensor == null) {
            return;
        }
        this.temperatureSensors.add(temperatureSensor);
    }
     */


    public Set<LightSource> getLightSources() {
        return lightSources;
    }

    public void addLightSource(LightSource lightSource) {
        if (lightSource == null) {
            return;
        }
        this.lightSources.add(lightSource);
    }

    @JsonIgnore
    public List<PeopleInRoom> getPeopleInRooms() {
        return peopleInRooms;
    }

    public double getTemperature() {
        return airQualityDevices.stream()
                .mapToDouble(AirQualityDevice::getTemperature)
                .max().orElse(-273.15d);

    }

    public double getHumidity() {
        return airQualityDevices.stream()
                .mapToDouble(AirQualityDevice::getHumidity)
                .max().orElse(-1.0d);
    }

    public double getCo2() {
        return airQualityDevices.stream()
                .mapToDouble(AirQualityDevice::getCo2)
                .max().orElse(-1.0d);
    }
}