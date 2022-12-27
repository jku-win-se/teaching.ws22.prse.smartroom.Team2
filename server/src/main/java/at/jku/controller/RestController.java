package at.jku.controller;

import at.jku.model.*;
import at.jku.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final RoomRepository roomRepository;
    private final DoorRepository doorRepository;
    private final WindoRepository windoRepository;
    private final LightSourceRepository lightSourceRepository;
    private final LightSourceRecordRepository lightSourceRecordRepository;
    private final VentilatorRepository ventilatorRepository;
    private final TemperatureSensorRepository temperatureSensorRepository;
    private final Co2SensorRepository co2SensorRepository;
    private final HumiditySensorRepository humiditySensorRepository;
    private final PeopleInRoomRepository peopleInRoomRepository;


    public RestController(final RoomRepository roomRepository,
                          final DoorRepository doorRepository,
                          final WindoRepository windoRepository,
                          final LightSourceRepository lightSourceRepository,
                          final VentilatorRepository ventilatorRepository,
                          final TemperatureSensorRepository temperatureSensorRepository,
                          final Co2SensorRepository co2SensorRepository,
                          final HumiditySensorRepository humiditySensorRepository,
                          final LightSourceRecordRepository lightSourceRecordRepository,
                          final PeopleInRoomRepository peopleInRoomRepository) {
        this.roomRepository = roomRepository;
        this.doorRepository = doorRepository;
        this.windoRepository = windoRepository;
        this.lightSourceRepository = lightSourceRepository;
        this.ventilatorRepository = ventilatorRepository;
        this.temperatureSensorRepository = temperatureSensorRepository;
        this.co2SensorRepository = co2SensorRepository;
        this.humiditySensorRepository = humiditySensorRepository;
        this.lightSourceRecordRepository = lightSourceRecordRepository;
        this.peopleInRoomRepository = peopleInRoomRepository;
    }

    //ROOMS
    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> addRoom(@RequestParam Optional<String> name,
                                        @RequestParam Optional<Integer> size) {
        final Room room = new Room();
        name.ifPresent(room::setName);
        size.ifPresent(room::setSize);
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }

    @GetMapping(value = "/rooms/{room_id:.*}")
    public ResponseEntity<Room> getRoom(@PathVariable Long room_id) {
        return ResponseEntity.ok(roomRepository.findById(room_id).orElse(null));
    }

    @PutMapping(value = "/rooms/{room_id:.*}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long room_id,
                                           @RequestParam Optional<String> name,
                                           @RequestParam Optional<Integer> size) {
        Optional<Room> room = roomRepository.findById(room_id);
        if (room.isPresent()) {
            name.ifPresent(s -> room.get().setName(s));
            size.ifPresent(integer -> room.get().setSize(integer));
        }
        roomRepository.save(room.get());
        return ResponseEntity.ok(room.get());
    }

    @DeleteMapping(value = "/rooms/{room_id:.*}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long room_id) {
        Room room = roomRepository.findById(room_id).orElse(null);
        roomRepository.delete(room);
        return ResponseEntity.ok(room);
    }

    @GetMapping(value = "/rooms/{room_id:.*}/PeopleInRoom")
    public ResponseEntity<List<PeopleInRoom>> getPeopleInRoom(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        return ResponseEntity.ok(room.get().getPeopleInRooms().stream().collect(Collectors.toList()));
    }

    @PostMapping(value = "/rooms/{room_id:.*}/PeopleInRoom")
    public ResponseEntity<PeopleInRoom> chgPeopleInRoom(@PathVariable Long room_id,
                                                        @RequestParam Optional<Integer> people_count) {
        final Optional<Room> room = roomRepository.findById(room_id);
        PeopleInRoom pir = new PeopleInRoom();
        if (room.isPresent() && people_count.isPresent()) {
            pir.setTimestamp(LocalDateTime.now());
            pir.setRoom(room.get());
            pir.setNOPeopleInRoom(people_count.get());
            room.get().addPeopleInRoom(pir);
            peopleInRoomRepository.save(pir);
        }
        return ResponseEntity.ok(room.get().getNumPeopleInRoom());
    }

    //LIGHTSOURCES
    @GetMapping(value = "/rooms/{room_id:.*}/lights")
    public ResponseEntity<List<LightSource>> getRoomLights(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        return ResponseEntity.ok(room.get().getLightSources().stream().collect(Collectors.toList()));
    }

    @PostMapping(value = "/rooms/{room_id}/lights")
    public ResponseEntity<LightSource> addLightSource(@PathVariable Long room_id,
                                                      @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final LightSource ls = new LightSource();
        if (name.isPresent()) {
            ls.setName(name.get());
        }
        if (room.isPresent()) {
            room.get().addLightSource(ls);
            ls.setRoom(room.get());
            lightSourceRepository.save(ls);
        }
        return ResponseEntity.ok(ls);
    }

    @GetMapping(value = "/rooms/{room_id:.*}/lights/{light_id:.*}")
    public ResponseEntity<LightSource> getLightSource(@PathVariable Long room_id,
                                                      @PathVariable Long light_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<LightSource> ls = room.get().getLightSources().stream().filter(l -> l.getId().equals(light_id)).findFirst();
        return ResponseEntity.ok(ls.orElse(null));
    }


    @PutMapping(value = "/rooms/{room_id:.*}/lights/{light_id:.*}")
    public ResponseEntity<LightSource> updateLightSource(@PathVariable Long room_id,
                                                         @PathVariable Long light_id,
                                                         @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<LightSource> ls = room.get().getLightSources().stream().filter(l -> l.getId().equals(light_id)).findFirst();
        if (room.isPresent() && ls.isPresent()) {
            if (name.isPresent()) {
                ls.get().setName(name.get());
                lightSourceRepository.save(ls.get());
            }
        }
        return ResponseEntity.ok(ls.orElse(null));
    }

    @GetMapping(value = "/rooms/{room_id:.*}/lights/{light_id:.*}/Activation")
    public ResponseEntity<LightSource> getLightSourceStatus(@PathVariable Long room_id,
                                                            @PathVariable Long light_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<LightSource> ls = room.get()
                .getLightSources().stream()
                .filter(l -> l.getId().equals(light_id)).findFirst();
        return ResponseEntity.ok(ls.orElse(null));
    }

    @PostMapping(value = "/rooms/{room_id:.*}/lights/{light_id:.*}/Activation")
    public ResponseEntity<LightSource> chgLightSourceStatus(@PathVariable Long room_id,
                                                            @PathVariable Long light_id,
                                                            @RequestParam Optional<Boolean> turnon) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<LightSource> ls = room.get()
                .getLightSources().stream()
                .filter(l -> l.getId().equals(light_id)).findFirst();
        if (room.isPresent() && ls.isPresent() && turnon.isPresent()) {
            LightSourceRecord lsr = new LightSourceRecord();
            lsr.setTimestamp(LocalDateTime.now());
            lsr.setState(turnon.get());
            lsr.setLightSource(ls.get());
            ls.get().addLightSourceRecord(lsr);
            lightSourceRecordRepository.save(lsr);
        }
        return ResponseEntity.ok(ls.orElse(null));
    }


    @DeleteMapping(value = "/rooms/{room_id:.*}/lights/{light_id:.*}")
    public ResponseEntity<LightSource> deleteLightSource(@PathVariable Long room_id,
                                                         @PathVariable Long light_id) {
        final Room room = roomRepository.findById(room_id).orElse(null);
        final LightSource ls = room.getLightSources().stream()
                .filter(l -> l.getId().equals(light_id)).findFirst().orElse(null);
        room.getLightSources().remove(ls);
        lightSourceRepository.delete(ls);
        return ResponseEntity.ok(ls);
    }

    //TODO
    //GET ACTIVATE LIGHT
    //POST ACTIVATE LIGHT
    //POST SET COLOR

    //------------------------------
    //------------------------------

    //VENTILATOR
    @GetMapping(value = "/rooms/{room_id:.*}/ventilators")
    public ResponseEntity<List<Ventilator>> getVentilators(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        return ResponseEntity.ok(room.get().getVentilators().stream().collect(Collectors.toList()));
    }

    @PostMapping(value = "/rooms/{room_id}/ventilators")
    public ResponseEntity<Ventilator> addVentilator(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Ventilator ventilator = new Ventilator();
        room.get().addVentilator(ventilator);
        if (room.isPresent()) {
            ventilator.setRoom(room.orElse(null));
            ventilatorRepository.save(ventilator);
        }
        return ResponseEntity.ok(ventilator);
    }

    @GetMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}")
    public ResponseEntity<Ventilator> getVentilator(@PathVariable Long room_id,
                                                    @PathVariable Long ventilator_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilator_id)).findFirst();
        return ResponseEntity.ok(vent.orElse(null));
    }

    @PutMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}")
    public ResponseEntity<Ventilator> updateVentilator(@PathVariable Long room_id,
                                                       @PathVariable Long ventilator_id,
                                                       @RequestParam boolean state) {
        final Room room = roomRepository.getById(room_id);
        final Optional<Ventilator> vent = room.getVentilators().stream().filter(l -> l.getId().equals(ventilator_id)).findFirst();
        if (vent.isPresent()) {
            vent.get().setState(state);
        }
        return ResponseEntity.ok(vent.orElse(null));
    }

    @DeleteMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}")
    public ResponseEntity<Ventilator> deleteVentilator(@PathVariable Long room_id,
                                                       @PathVariable Long ventilator_id) {
        final Room room = roomRepository.findById(room_id).orElse(null);
        final Ventilator vent = room.getVentilators().stream()
                .filter(l -> l.getId().equals(ventilator_id)).findFirst().orElse(null);
        room.getVentilators().remove(vent);
        ventilatorRepository.delete(vent);
        return ResponseEntity.ok(vent);
    }

    //TODO
    //POST ACTIVATE VENTILATOR
    //GET ACTIVATE VENTILATOR
    //POST ACTIVATE VENTILATOR

    //------------------------------
    //------------------------------

    //WINDOWS
    @GetMapping(value = "/rooms/{room_id:.*}/windows")
    public ResponseEntity<List<Windo>> getWindows(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        return ResponseEntity.ok(room.get().getWindows().stream().collect(Collectors.toList()));
    }

    @PostMapping(value = "/rooms/{room_id:.*}/windows")
    public ResponseEntity<Windo> addWindow(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Windo window = new Windo();
        room.get().addWindow(window);
        if (room.isPresent()) {
            window.setRoom(room.orElse(null));
            windoRepository.save(window);
        }
        return ResponseEntity.ok(window);
    }


    @GetMapping(value = "/rooms/{room_id:.*}/window_id/{window_id:.*}")
    public ResponseEntity<Windo> getWindow(@PathVariable Long room_id,
                                           @PathVariable Long window_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        Optional<Windo> window = room.get().getWindows().stream().filter(l -> l.getId().equals(window_id)).findFirst();
        return ResponseEntity.ok(window.orElse(null));
    }

    @PutMapping(value = "/rooms/{room_id:.*}/window_id/{window_id:.*}")
    public ResponseEntity<Windo> updateWindow(@PathVariable Long room_id,
                                              @PathVariable Long window_id) {
        final Room room = roomRepository.getById(room_id);
        final Optional<Windo> window = room.getWindows().stream().filter(l -> l.getId().equals(window_id)).findFirst();
        return ResponseEntity.ok(window.orElse(null));
    }

    @DeleteMapping(value = "/rooms/{room_id:.*}/window_id/{window_id:.*}")
    public ResponseEntity<Windo> deleteWindow(@PathVariable Long room_id,
                                              @PathVariable Long window_id) {
        final Room room = roomRepository.findById(room_id).orElse(null);
        final Windo windo = room.getWindows().stream()
                .filter(l -> l.getId().equals(window_id)).findFirst().orElse(null);
        room.getVentilators().remove(windo);
        windoRepository.delete(windo);
        return ResponseEntity.ok(windo);
    }

    //TODO
    //GET OPEN WINDOW
    //POST OPEN WINDOW

    //------------------------------
    //------------------------------

    //DOOR
    @GetMapping(value = "/rooms/{room_id:.*}/doors")
    public ResponseEntity<List<Door>> getDoors(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        return ResponseEntity.ok(room.get().getDoors().stream().collect(Collectors.toList()));
    }

    @PostMapping(value = "/rooms/{room_id:.*}/doors")
    public ResponseEntity<Door> addDoor(@PathVariable Long room_id,
                                        @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Door door = new Door();
        if (room.isPresent()) {
            if (name.isPresent()) {
                door.setName(name.get());
            }
            room.get().addDoor(door);
            door.addRoom(room.get());
            doorRepository.save(door);
        }
        return ResponseEntity.ok(door);
    }


    @GetMapping(value = "/rooms/{room_id:.*}/doors/{door_id:.*}")
    public ResponseEntity<Door> getDoor(@PathVariable Long room_id, @PathVariable Long door_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        Optional<Door> ls = room.get().getDoors().stream().filter(l -> l.getId().equals(door_id)).findFirst();
        return ResponseEntity.ok(ls.orElse(null));
    }

    //UPDATE DOOR

    @PutMapping(value = "/rooms/{room_id:.*}/doors/{door_id:.*}")
    public ResponseEntity<Door> updateDoor(@PathVariable Long room_id,
                                           @PathVariable Long door_id,
                                           @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Door> door = room.get().getDoors().stream().filter(l -> l.getId().equals(door_id)).findFirst();
        if (door.isPresent()) {
            door.get().setName(name.get());
            doorRepository.save(door.get());
        }
        return ResponseEntity.ok(door.orElse(null));
    }

    //DELETE DOOR

    @DeleteMapping(value = "/rooms/{room_id:.*}/doors/{door_id:.*}")
    public ResponseEntity<Door> deleteDoor(@PathVariable Long room_id,
                                           @PathVariable Long door_id) {
        final Room room = roomRepository.findById(room_id).orElse(null);
        final Door gd = room.getDoors().stream()
                .filter(l -> l.getId().equals(door_id)).findFirst().orElse(null);
        room.getDoors().remove(gd);
        doorRepository.delete(gd);
        return ResponseEntity.ok(gd);
    }
    /** TODO: GET OPEN DOOR
     TODO: POST OPEN DOOR
     */
 /*
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

    @RequestMapping(value = "/co2sensors/{co2sensor_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<Co2Sensor> getCo2Sensor(@PathVariable Long co2sensor_id) {
        return ResponseEntity.ok(co2SensorRepository.findById(co2sensor_id).orElse(null));
    }

    @RequestMapping(value = "/co2sensors/{co2sensor_id:.*}", method = RequestMethod.PUT)
    public ResponseEntity<Co2Sensor> updateCo2Sensor(@PathVariable Long co2sensor_id,
                                             @RequestParam Optional<Room> room) {
        Co2Sensor co2Sensor = co2SensorRepository.findById(co2sensor_id).orElse(null);
        if (co2Sensor != null) {
            if (room.isPresent()) {
                co2Sensor.setRoom(room.get());
            }
        }
        co2SensorRepository.save(co2Sensor);
        return ResponseEntity.ok(co2Sensor);
    }

    @RequestMapping(value = "/co2sensors/{co2sensor_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<Co2Sensor> deleteCo2Sensor(@PathVariable Long co2sensor_id) {
        Co2Sensor co2Sensor = co2SensorRepository.findById(co2sensor_id).orElse(null);
        co2SensorRepository.delete(co2Sensor);
        return ResponseEntity.ok(co2Sensor);
    }


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

    @RequestMapping(value = "/humiditysensors/{humiditysensor_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<HumiditySensor> getHumiditySensor(@PathVariable Long humiditysensor_id) {
        return ResponseEntity.ok(humiditySensorRepository.findById(humiditysensor_id).orElse(null));
    }

    @RequestMapping(value = "/humiditysensors/{humiditysensor_id:.*}", method = RequestMethod.PUT)
    public ResponseEntity<HumiditySensor> updateHumiditySensor(@PathVariable Long humiditysensor_id,
                                                     @RequestParam Optional<Room> room) {
        HumiditySensor humiditySensor = humiditySensorRepository.findById(humiditysensor_id).orElse(null);
        if (humiditySensor != null) {
            if (room.isPresent()) {
                humiditySensor.setRoom(room.get());
            }
        }
        humiditySensorRepository.save(humiditySensor);
        return ResponseEntity.ok(humiditySensor);
    }

    @RequestMapping(value = "/humiditysensors/{humiditysensor_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<HumiditySensor> deleteHumiditySensor(@PathVariable Long humiditysensor_id) {
        HumiditySensor humiditySensor = humiditySensorRepository.findById(humiditysensor_id).orElse(null);
        humiditySensorRepository.delete(humiditySensor);
        return ResponseEntity.ok(humiditySensor);
    }

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
    @RequestMapping(value = "/temperaturesensors/{temperatursensor_id:.*}", method = RequestMethod.GET)
    public ResponseEntity<TemperatureSensor> getTemperaturSensor(@PathVariable Long temperatursensor_id) {
        return ResponseEntity.ok(temperatureSensorRepository.findById(temperatursensor_id).orElse(null));
    }

    @RequestMapping(value = "/humiditysensors/{humiditysensor_id:.*}", method = RequestMethod.PUT)
    public ResponseEntity<TemperatureSensor> updateTemperaturSensor(@PathVariable Long temperatursensor_id,
                                                               @RequestParam Optional<Room> room) {
        TemperatureSensor temperatureSensor = temperatureSensorRepository.findById(temperatursensor_id).orElse(null);
        if (temperatureSensor != null) {
            if (room.isPresent()) {
                temperatureSensor.setRoom(room.get());
            }
        }
        temperatureSensorRepository.save(temperatureSensor);
        return ResponseEntity.ok(temperatureSensor);
    }

    @RequestMapping(value = "/humiditysensors/{humiditysensor_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<TemperatureSensor> deleteTemperaturSensor(@PathVariable Long temperatursensor_id) {
        TemperatureSensor temperatureSensor = temperatureSensorRepository.findById(temperatursensor_id).orElse(null);
        temperatureSensorRepository.delete(temperatureSensor);
        return ResponseEntity.ok(temperatureSensor);
    }




    //------------------------------
    //------------------------------
     */


    //------------------------------
    //------------------------------
}

