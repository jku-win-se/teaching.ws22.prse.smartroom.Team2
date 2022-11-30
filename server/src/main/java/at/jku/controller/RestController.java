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
    private AirQualitySensorRepository airQualitySensorRepository;


    public RestController(final RoomRepository roomRepository,
                          final DoorRepository doorRepository,
                          final WindoRepository windoRepository,
                          final LightSourceRepository lightSourceRepository,
                          final VentilatorRepository ventilatorRepository,
                          final AirQualitySensorRepository airQualitySensorRepositor
    ) {
        this.roomRepository = roomRepository;
        this.doorRepository = doorRepository;
        this.windoRepository = windoRepository;
        this.lightSourceRepository = lightSourceRepository;
        this.ventilatorRepository = ventilatorRepository;
        this.airQualitySensorRepository = airQualitySensorRepositor;
    }

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

    // --------------- Test Adapt Incomplete ------------------------
    // --------------------------------------------------------------
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

    @GetMapping("/airqualitysensors")
    public ResponseEntity<List<AirQualitySensor>> getAllAirQualitySensors() {
        return ResponseEntity.ok(airQualitySensorRepository.findAll());
    }

    @PostMapping("/airqualitysensors")
    public ResponseEntity<AirQualitySensor> addLightAirQualitySensor() {
        final AirQualitySensor airQualitySensor = new AirQualitySensor();
        airQualitySensorRepository.save(airQualitySensor);
        return ResponseEntity.ok(airQualitySensor);
    }


    // --------------------------------------------------------------
    // --------------------------------------------------------------

}

