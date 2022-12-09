package at.jku.controller;

import at.jku.model.*;
import at.jku.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private RoomRepository roomRepository;
    private DoorRepository doorRepository;
    private WindoRepository windoRepository;
    private LightSourceRepository lightSourceRepository;
    private VentilatorRepository ventilatorRepository;
    private TemperatureSensorRepository temperatureSensorRepository;
    private Co2SensorRepository co2SensorRepository;
    private HumiditySensorRepository humiditySensorRepository;


    public RestController(final RoomRepository roomRepository,
                          final DoorRepository doorRepository,
                          final WindoRepository windoRepository,
                          final LightSourceRepository lightSourceRepository,
                          final VentilatorRepository ventilatorRepository,
                          final TemperatureSensorRepository temperatureSensorRepository,
                          final Co2SensorRepository co2SensorRepository,
                          final HumiditySensorRepository humiditySensorRepository
    ) {
        this.roomRepository = roomRepository;
        this.doorRepository = doorRepository;
        this.windoRepository = windoRepository;
        this.lightSourceRepository = lightSourceRepository;
        this.ventilatorRepository = ventilatorRepository;
        this.temperatureSensorRepository = temperatureSensorRepository;
        this.co2SensorRepository = co2SensorRepository;
        this.humiditySensorRepository = humiditySensorRepository;
    }

    //ROOMS
    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> addRoom(String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final Room room = new Room(name);
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }

    @RequestMapping(value = "/rooms/{room_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<Room> getRoom(@PathVariable Long room_id) {
        return ResponseEntity.ok(roomRepository.findById(room_id).orElse(null));
    }

    @RequestMapping(value = "/rooms/{room_id:.*}", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateRoom(@PathVariable Long room_id,
                                           @RequestParam Optional<String> name,
                                           @RequestParam Optional<Integer> size) {
        Room room = roomRepository.findById(room_id).orElse(null);
        if (room != null) {
            if (name.isPresent()) {
                room.setName(name.get());
            }
            if (size.isPresent()) {
                room.setSize(size.get());
            }
        }
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }

    @RequestMapping(value = "/rooms/{room_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<Room> deleteRoom(@PathVariable Long room_id) {
        Room room = roomRepository.findById(room_id).orElse(null);
        roomRepository.delete(room);
        return ResponseEntity.ok(room);
    }

    //TODO
    //GET PEOPLEINROOM
    //POST PEOPLEINROOM

    //------------------------------
    //------------------------------

    //LIGHTSOURCES
    @GetMapping("/lightsources")
    public ResponseEntity<List<LightSource>> getAllLightSources() {
        return ResponseEntity.ok(lightSourceRepository.findAll());
    }

    @PostMapping("/lightsources")
    public ResponseEntity<LightSource> addLightSource() {
        final LightSource lightSource = new LightSource();
        lightSourceRepository.save(lightSource);
        return ResponseEntity.ok(lightSource);
    }

    @RequestMapping(value = "/lightsources/{light_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<LightSource> getLightSource(@PathVariable Long light_id) {
        return ResponseEntity.ok(lightSourceRepository.findById(light_id).orElse(null));
    }

    @RequestMapping(value = "/lightsources/{light_id:.*}", method = RequestMethod.PUT)
    public ResponseEntity<LightSource> updateLightSource(@PathVariable Long ventilator_id,
                                                         @RequestParam Optional<Room> room) {
        LightSource lightSource = lightSourceRepository.findById(ventilator_id).orElse(null);
        if (lightSource != null) {
            if (room.isPresent()) {
                lightSource.setRoom(room.get());
            }
        }
        lightSourceRepository.save(lightSource);
        return ResponseEntity.ok(lightSource);
    }

    @RequestMapping(value = "/lightsources/{light_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<LightSource> deleteLightSource(@PathVariable Long light_id) {
        LightSource light = lightSourceRepository.findById(light_id).orElse(null);
        lightSourceRepository.delete(light);
        return ResponseEntity.ok(light);
    }

    //TODO
    //GET ACTIVATE LIGHT
    //POST ACTIVATE LIGHT
    //POST SET COLOR

    //------------------------------
    //------------------------------

    //VENTILATOR
    @GetMapping("/ventilators")
    public ResponseEntity<List<Ventilator>> getAllVentilators() {
        return ResponseEntity.ok(ventilatorRepository.findAll());
    }

    @PostMapping("/ventilators")
    public ResponseEntity<Ventilator> addVentilator() {
        final Ventilator ventilator = new Ventilator();
        ventilatorRepository.save(ventilator);
        return ResponseEntity.ok(ventilator);
    }

    @RequestMapping(value = "/ventilators/{ventilator_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<Ventilator> getVentilator(@PathVariable Long ventilator_id) {
        return ResponseEntity.ok(ventilatorRepository.findById(ventilator_id).orElse(null));
    }

    @RequestMapping(value = "/ventilators/{ventilator_id:.*}", method = RequestMethod.PUT)
    public ResponseEntity<Ventilator> updateVentilator(@PathVariable Long ventilator_id,
                                                       @RequestParam Optional<Room> room) {
        Ventilator ventilator = ventilatorRepository.findById(ventilator_id).orElse(null);
        if (ventilator != null) {
            if (room.isPresent()) {
                ventilator.setRoom(room.get());
            }
        }
        ventilatorRepository.save(ventilator);
        return ResponseEntity.ok(ventilator);
    }

    @RequestMapping(value = "/ventilators/{ventilator_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<Ventilator> deleteVentilator(@PathVariable Long ventilator_id) {
        Ventilator ventilator = ventilatorRepository.findById(ventilator_id).orElse(null);
        ventilatorRepository.delete(ventilator);
        return ResponseEntity.ok(ventilator);
    }

    //TODO
    //POST ACTIVATE VENTILATOR
    //GET ACTIVATE VENTILATOR
    //POST ACTIVATE VENTILATOR

    //------------------------------
    //------------------------------

    //DOOR
    @GetMapping("/doors")
    public ResponseEntity<List<Door>> getAllDoors() {
        return ResponseEntity.ok(doorRepository.findAll());
    }

    @PostMapping("/doors")
    public ResponseEntity<Door> addDoor() {
        final Door door = new Door();
        doorRepository.save(door);
        return ResponseEntity.ok(door);
    }

    @RequestMapping(value = "/doors/{doors_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<Door> getDoor(@PathVariable Long doors_id) {
        return ResponseEntity.ok(doorRepository.findById(doors_id).orElse(null));
    }

    //TODO
    //UPDATE DOOR
    //DELETE DOOR
    //GET OPEN DOOR
    //POST OPEN DOOR

    //------------------------------
    //------------------------------

    //WINDOWS
    @GetMapping("/windows")
    public ResponseEntity<List<Windo>> getAllWindows() {
        return ResponseEntity.ok(windoRepository.findAll());
    }

    @PostMapping("/windows")
    public ResponseEntity<Windo> addWindow() {
        final Windo windo = new Windo();
        windoRepository.save(windo);
        return ResponseEntity.ok(windo);
    }

    @RequestMapping(value = "/windows/{windo_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<Windo> getWindo(@PathVariable Long windo_id) {
        return ResponseEntity.ok(windoRepository.findById(windo_id).orElse(null));
    }

    @RequestMapping(value = "/windows/{windo_id:.*}", method = RequestMethod.PUT)
    public ResponseEntity<Windo> updateWindo(@PathVariable Long windo_id,
                                             @RequestParam Optional<Room> room) {
        Windo windo = windoRepository.findById(windo_id).orElse(null);
        if (windo != null) {
            if (room.isPresent()) {
                windo.setRoom(room.get());
            }
        }
        windoRepository.save(windo);
        return ResponseEntity.ok(windo);
    }

    @RequestMapping(value = "/windows/{windo_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<Windo> deleteWindo(@PathVariable Long windo_id) {
        Windo windo = windoRepository.findById(windo_id).orElse(null);
        windoRepository.delete(windo);
        return ResponseEntity.ok(windo);
    }

    //TODO
    //GET OPEN WINDOW
    //POST OPEN WINDOW

    //------------------------------
    //------------------------------

    //CO2SENSOR
    @GetMapping("/co2sensors")
    public ResponseEntity<List<Co2Sensor>> getAllCo2Sensors() {
        return ResponseEntity.ok(co2SensorRepository.findAll());
    }

    @PostMapping("/co2sensors")
    public ResponseEntity<Co2Sensor> addCo2Sensor() {
        final Co2Sensor co2Sensor = new Co2Sensor();
        co2SensorRepository.save(co2Sensor);
        return ResponseEntity.ok(co2Sensor);
    }

    //TODO
    //GET
    //UPDATE
    //DELETE

    //------------------------------
    //------------------------------

    //HUMIDITYSENSOR
    @GetMapping("/humiditysensors")
    public ResponseEntity<List<HumiditySensor>> getAllHumiditySensors() {
        return ResponseEntity.ok(humiditySensorRepository.findAll());
    }

    @PostMapping("/humiditysensors")
    public ResponseEntity<HumiditySensor> addHumiditySensors() {
        final HumiditySensor humiditySensor = new HumiditySensor();
        humiditySensorRepository.save(humiditySensor);
        return ResponseEntity.ok(humiditySensor);
    }

    //TODO
    //GET
    //UPDATE
    //DELETE

    //------------------------------
    //------------------------------

    //TEMPERATURESENSORS
    @GetMapping("/temperaturesensors")
    public ResponseEntity<List<TemperatureSensor>> getAllTemperatureSensors() {
        return ResponseEntity.ok(temperatureSensorRepository.findAll());
    }

    @PostMapping("/temperaturesensors")
    public ResponseEntity<TemperatureSensor> addTemperatureSensor() {
        final TemperatureSensor temperatureSensor = new TemperatureSensor();
        temperatureSensorRepository.save(temperatureSensor);
        return ResponseEntity.ok(temperatureSensor);
    }

    //TODO
    //GET
    //UPDATE
    //DELETE

    //------------------------------
    //------------------------------
}

