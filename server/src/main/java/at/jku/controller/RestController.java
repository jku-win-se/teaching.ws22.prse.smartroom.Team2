package at.jku.controller;

import at.jku.model.*;
import at.jku.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final RoomRepository roomRepository;
    private final DoorRepository doorRepository;
    private final DoorRecordRepository doorRecordRepository;
    private final WindoRepository windoRepository;
    private final LightSourceRepository lightSourceRepository;
    private final LightSourceRecordRepository lightSourceRecordRepository;
    private final VentilatorRepository ventilatorRepository;
    private final VentilatorRecordRepository ventilatorRecordRepository;
    private final TemperatureSensorRepository temperatureSensorRepository;
    private final TemperatureSensorRecordRepository temperatureSensorRecordRepository;
    private final Co2SensorRepository co2SensorRepository;
    private final Co2SensorRecordRepository co2SensorRecordRepository;
    private final HumiditySensorRepository humiditySensorRepository;
    private final HumiditySensorRecordRepository humiditySensorRecordRepository;
    private final PeopleInRoomRepository peopleInRoomRepository;
    private final WindowRecordRepository windowRecordRepository;
    private final AirQualityDeviceRepository airQualityDeviceRepository;
    private final AirQualityDeviceRecordRepository airQualityDeviceRecordRepository;

    public RestController(final RoomRepository roomRepository,
                          final DoorRepository doorRepository,
                          final WindoRepository windoRepository,
                          final LightSourceRepository lightSourceRepository,
                          final VentilatorRepository ventilatorRepository,
                          final TemperatureSensorRepository temperatureSensorRepository,
                          final Co2SensorRepository co2SensorRepository,
                          final HumiditySensorRepository humiditySensorRepository,
                          final LightSourceRecordRepository lightSourceRecordRepository,
                          final PeopleInRoomRepository peopleInRoomRepository,
                          final VentilatorRecordRepository ventilatorRecordRepository,
                          final DoorRecordRepository doorRecordRepository,
                          final WindowRecordRepository windowRecordRepository,
                          final TemperatureSensorRecordRepository temperatureSensorRecordRepository,
                          final Co2SensorRecordRepository co2SensorRecordRepository,
                          final HumiditySensorRecordRepository humiditySensorRecordRepository,
                          final AirQualityDeviceRepository airQualityDeviceRepository,
                          final AirQualityDeviceRecordRepository airQualityDeviceRecordRepository) {
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
        this.ventilatorRecordRepository = ventilatorRecordRepository;
        this.doorRecordRepository = doorRecordRepository;
        this.windowRecordRepository = windowRecordRepository;
        this.temperatureSensorRecordRepository = temperatureSensorRecordRepository;
        this.co2SensorRecordRepository = co2SensorRecordRepository;
        this.humiditySensorRecordRepository = humiditySensorRecordRepository;
        this.airQualityDeviceRepository = airQualityDeviceRepository;
        this.airQualityDeviceRecordRepository = airQualityDeviceRecordRepository;
    }

    // ============================== ROOMS =====================================
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

    // ============================== PEOPLE IN ROOMS =====================================

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

    // ============================== LIGHTS =====================================
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


    @PatchMapping(value = "/rooms/{room_id:.*}/lights/{light_id:.*}")
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

    @PostMapping(value = "/rooms/{room_id:.*}/lights/{light_id:.*}/SetColor")
    public ResponseEntity<LightSource> chgLightSourceColor(@PathVariable Long room_id,
                                                           @PathVariable Long light_id,
                                                           @RequestParam Optional<String> hex,
                                                           @RequestParam Optional<Integer> brightness) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<LightSource> ls = room.get()
                .getLightSources().stream()
                .filter(l -> l.getId().equals(light_id)).findFirst();
        if (room.isPresent() && ls.isPresent()) {
            if (hex.isPresent()) {
                ls.get().setHex(hex.get());
            }
            if (brightness.isPresent()) {
                ls.get().setBrightness(brightness.get());
            }
            lightSourceRepository.save(ls.get());
        }
        return ResponseEntity.ok(ls.orElse(null));
    }


    // ============================== VENTILATORS =====================================
    @GetMapping(value = "/rooms/{room_id:.*}/ventilators")
    public ResponseEntity<List<Ventilator>> getVentilators(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        return ResponseEntity.ok(room.get().getVentilators().stream().collect(Collectors.toList()));
    }

    @PostMapping(value = "/rooms/{room_id}/ventilators")
    public ResponseEntity<Ventilator> addVentilator(@PathVariable Long room_id,
                                                    @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Ventilator ventilator = new Ventilator();

        if (room.isPresent()) {
            room.get().addVentilator(ventilator);
            name.ifPresent(ventilator::setName);
            ventilator.setRoom(room.get());
            ventilatorRepository.save(ventilator);
        }
        return ResponseEntity.ok(ventilator);
    }

    @GetMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}")
    public ResponseEntity<Ventilator> getVentilator(@PathVariable Long room_id,
                                                    @PathVariable Long ventilator_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilator_id)).findFirst();
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

    @PatchMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}")
    public ResponseEntity<Ventilator> updateVentilator(@PathVariable Long room_id,
                                                       @PathVariable Long ventilator_id,
                                                       @RequestParam String name) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilator_id)).findFirst();
        vent.ifPresent(ventilator -> ventilator.setName(name));
        ventilatorRepository.save(vent.get());
        return ResponseEntity.ok(vent.orElse(null));
    }

    @PostMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}/Operations")
    public ResponseEntity<Ventilator> postVentilatorState(@PathVariable Long room_id,
                                                          @PathVariable Long ventilator_id,
                                                          @RequestParam Optional<Boolean> turnon) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilator_id)).findFirst();

        if (vent.isPresent() && turnon.isPresent()) {
            VentilatorRecord vr = new VentilatorRecord();
            vr.setVentilator(vent.get());
            vr.setTimestamp(LocalDateTime.now());
            vr.setState(turnon.get());
            vent.get().addVentilatorRecord(vr);
            ventilatorRecordRepository.save(vr);
        }
        return ResponseEntity.ok(vent.orElse(null));
    }

    @GetMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}/Activation")
    public ResponseEntity<VentilatorRecord> getVentilatorState(@PathVariable Long room_id,
                                                               @PathVariable Long ventilator_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilator_id)).findFirst();
        final Optional<VentilatorRecord> vr = vent.get().getLatestVentilatorRecord();
        return ResponseEntity.ok(vr.get());
    }

    @PostMapping(value = "/rooms/{room_id:.*}/ventilators/{ventilator_id:.*}/Activation")
    public ResponseEntity<Ventilator> activateVentilator(@PathVariable Long room_id,
                                                         @PathVariable Long ventilator_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilator_id)).findFirst();

        if (vent.isPresent()) {
            VentilatorRecord vr = new VentilatorRecord();
            vr.setVentilator(vent.get());
            vr.setTimestamp(LocalDateTime.now());
            vr.setState(!(vent.get().getState()));
            vent.get().addVentilatorRecord(vr);
            ventilatorRecordRepository.save(vr);
        }
        return ResponseEntity.ok(vent.orElse(null));
    }

    // ============================== WINDOWS =====================================
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

    @GetMapping(value = "/rooms/{room_id:.*}/windows/{window_id:.*}")
    public ResponseEntity<Windo> getWindow(@PathVariable Long room_id,
                                           @PathVariable Long window_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        Optional<Windo> window = room.get().getWindows().stream().filter(l -> l.getId().equals(window_id)).findFirst();
        return ResponseEntity.ok(window.orElse(null));
    }

    @PutMapping(value = "/rooms/{room_id:.*}/windows/{window_id:.*}")
    public ResponseEntity<Windo> updateWindow(@PathVariable Long room_id,
                                              @PathVariable Long window_id) {
        final Room room = roomRepository.getById(room_id);
        final Optional<Windo> window = room.getWindows().stream().filter(l -> l.getId().equals(window_id)).findFirst();
        return ResponseEntity.ok(window.orElse(null));
    }

    @DeleteMapping(value = "/rooms/{room_id:.*}/windows/{window_id:.*}")
    public ResponseEntity<Windo> deleteWindow(@PathVariable Long room_id,
                                              @PathVariable Long window_id) {
        final Room room = roomRepository.findById(room_id).orElse(null);
        final Windo windo = room.getWindows().stream()
                .filter(l -> l.getId().equals(window_id)).findFirst().orElse(null);
        room.getVentilators().remove(windo);
        windoRepository.delete(windo);
        return ResponseEntity.ok(windo);
    }

    @GetMapping(value = "/rooms/{room_id:.*}/windows/{window_id:.*}/Open")
    public ResponseEntity<WindowRecord> getWindowState(@PathVariable Long room_id,
                                                       @PathVariable Long window_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Windo> win = room.get().getWindows().stream().filter(l -> l.getId().equals(window_id)).findFirst();
        final Optional<WindowRecord> wr = win.get().getLatestWindowRecord();
        return ResponseEntity.ok(wr.isPresent() ? wr.get() : null);
    }

    @PostMapping(value = "/rooms/{room_id:.*}/windows/{window_id:.*}/Open")
    public ResponseEntity<Windo> postWindowState(@PathVariable Long room_id,
                                                 @PathVariable Long window_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Windo> win = room.get().getWindows().stream().filter(l -> l.getId().equals(window_id)).findFirst();
        if (win.isPresent()) {
            final WindowRecord wr = new WindowRecord();
            wr.setWindow(win.get());
            wr.setTimestamp(LocalDateTime.now());
            wr.setState(!(win.get().getState()));
            win.get().addWindowRecord(wr);
            windowRecordRepository.save(wr);
        }
        return ResponseEntity.ok(win.orElse(null));
    }

    // ============================== DOORS =====================================
    @GetMapping(value = "/rooms/{room_id:.*}/doors")
    public ResponseEntity<List<Door>> getDoors(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        return ResponseEntity.ok(room.get().getDoors().stream().collect(Collectors.toList()));
    }

    @PutMapping(value = "/rooms/{room_id:.*}/doors")
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

    @PutMapping(value = "/rooms/{room_id:.*}/doors/{door_id:.*}")
    public ResponseEntity<Door> connectDoor(@PathVariable Long room_id,
                                            @PathVariable Long door_id,
                                            @RequestParam Long room2
                                        ) {
        final Optional<Room> room_1 = roomRepository.findById(room_id);
        final Optional<Room> room_2 = roomRepository.findById(room2);
        final Optional<Door> door = room_1.get().getDoors().stream()
                .filter(l -> l.getId().equals(door_id)).findFirst();

        if (room_1.isPresent()) {
            if (room_2.isPresent()) {
                if (door.isPresent()) {
               door.get().addRoom(room_2.get());
               room_2.get().addDoor(door.get());
            }}
        }
        doorRepository.save(door.get());
        roomRepository.save(room_2.get());
        return ResponseEntity.ok(door.get());
    }


    @GetMapping(value = "/rooms/{room_id:.*}/doors/{door_id:.*}")
    public ResponseEntity<Door> getDoor(@PathVariable Long room_id,
                                        @PathVariable Long door_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Door> ls = room.get().getDoors().stream()
                .filter(l -> l.getId().equals(door_id)).findFirst();
        return ResponseEntity.ok(ls.orElse(null));
    }

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

    @GetMapping(value = "/rooms/{room_id:.*}/doors/{door_id:.*}/Open")
    public ResponseEntity<DoorRecord> getDoorState(@PathVariable Long room_id,
                                                   @PathVariable Long door_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Door> door = room.get().getDoors().stream().filter(l -> l.getId().equals(door_id)).findFirst();
        final Optional<DoorRecord> dr = door.get().getLatestDoorRecord();


        return ResponseEntity.ok(dr.isPresent() ? dr.get() : null);
    }

    @PostMapping(value = "/rooms/{room_id:.*}/doors/{door_id:.*}/Open")
    public ResponseEntity<Door> postDoorState(@PathVariable Long room_id,
                                              @PathVariable Long door_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<Door> door = room.get().getDoors().stream().filter(l -> l.getId().equals(door_id)).findFirst();
        if (door.isPresent()) {
            final DoorRecord dr = new DoorRecord();
            dr.setDoor(door.get());
            dr.setTimestamp(LocalDateTime.now());
            dr.setState(!(door.get().getState()));
            door.get().addDoorRecord(dr);
            doorRecordRepository.save(dr);
        }
        return ResponseEntity.ok(door.orElse(null));
    }

    // ============================== AIR-QUALITY =====================================

    @GetMapping(value = "/room/{room_id:.*}/AirQuality")
    public ResponseEntity<List<AirQualityDevice>> getAirQuality(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        if (!room.isPresent()) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(room.get().getAirQualityDevices().stream().collect(Collectors.toList()));
    }

    @GetMapping(value = "/room/{room_id:.*}/AirQuality/temperature")
    public ResponseEntity<TemperatureSensorRecord> getAirQualityTemperature(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        if (!room.isPresent()) {
            return ResponseEntity.ok(null);
        }

        Optional<TemperatureSensorRecord> tsr =
                room.get().getAirQualityDevices().stream()
                        .map(aqd -> aqd.getTemperatureSensor().getLatestTemperatureSensorRecord())
                        .max(Comparator.comparing(t -> t.isPresent() ? t.get().getTimestamp() : null)).get();
        return ResponseEntity.ok(tsr.orElse(null));
    }

    @GetMapping(value = "/room/{room_id:.*}/AirQuality/humidity")
    public ResponseEntity<HumiditySensorRecord> getAirQualityHumidity(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        if (!room.isPresent()) {
            return ResponseEntity.ok(null);
        }
        Optional<HumiditySensorRecord> hsr =
                room.get().getAirQualityDevices().stream()
                        .map(aqd -> aqd.getHumiditySensor().getLatestHumiditySensorRecord())
                        .max(Comparator.comparing(t -> t.isPresent() ? t.get().getTimestamp() : null)).get();
        return ResponseEntity.ok(hsr.orElse(null));
    }

    @GetMapping(value = "/room/{room_id:.*}/AirQuality/co2")
    public ResponseEntity<Co2SensorRecord> getAirQualityCo2(@PathVariable Long room_id) {
        final Optional<Room> room = roomRepository.findById(room_id);
        if (!room.isPresent()) {
            return ResponseEntity.ok(null);
        }
        Optional<Co2SensorRecord> csr =
                room.get().getAirQualityDevices().stream()
                        .map(aqd -> aqd.getCo2Sensor().getLatestCo2SensorRecord())
                        .max(Comparator.comparing(t -> t.isPresent() ? t.get().getTimestamp() : null)).get();
        return ResponseEntity.ok(csr.orElse(null));
    }


    @PostMapping(value = "/room/AirQuality")
    public ResponseEntity<AirQualityDevice> addAirQuality(@RequestParam Long room_id,
                                                          @RequestParam Optional<Double> co2,
                                                          @RequestParam Optional<Double> humidity,
                                                          @RequestParam Optional<Double> temperature) {
        final Optional<Room> room = roomRepository.findById(room_id);
        if (!room.isPresent()) {
            return ResponseEntity.ok(null);
        }
        final AirQualityDevice airQualityDevice = new AirQualityDevice();
        final AirQualityDeviceRecord airQualityDeviceRecord = new AirQualityDeviceRecord();
        final Co2Sensor co2Sensor = new Co2Sensor();
        final Co2SensorRecord co2SensorRecord = new Co2SensorRecord();
        final TemperatureSensor temperatureSensor = new TemperatureSensor();
        final TemperatureSensorRecord temperatureSensorRecord = new TemperatureSensorRecord();
        final HumiditySensor humiditySensor = new HumiditySensor();
        final HumiditySensorRecord humiditySensorRecord = new HumiditySensorRecord();

        airQualityDevice.setRoom(room.get());
        airQualityDevice.setCo2Sensor(co2Sensor);
        airQualityDevice.setHumiditySensor(humiditySensor);
        airQualityDevice.setTemperatureSensor(temperatureSensor);
        airQualityDeviceRecord.setAirQualityDevice(airQualityDevice);
        airQualityDeviceRecord.setState(true);
        airQualityDeviceRecord.setTimestamp(LocalDateTime.now());

        co2Sensor.setAirQualityDevice(airQualityDevice);
        temperatureSensor.setAirQualityDevice(airQualityDevice);
        humiditySensor.setAirQualityDevice(airQualityDevice);

        co2SensorRecord.setCo2Sensor(co2Sensor);
        co2SensorRecord.setTimestamp(LocalDateTime.now());
        if (co2.isPresent()) {
            co2SensorRecord.setCo2(co2.get());
        }

        temperatureSensorRecord.setTemperatureSensor(temperatureSensor);
        temperatureSensorRecord.setTimestamp(LocalDateTime.now());
        if (temperature.isPresent()) {
            temperatureSensorRecord.setTemperature(temperature.get());
        }

        humiditySensorRecord.setHumiditySensor(humiditySensor);
        humiditySensorRecord.setTimestamp(LocalDateTime.now());
        if (humidity.isPresent()) {
            humiditySensorRecord.setHumidity(humidity.get());
        }

        co2Sensor.addCo2SensorRecord(co2SensorRecord);
        temperatureSensor.addTemperatureSensorRecord(temperatureSensorRecord);
        humiditySensor.addHumiditySensorRecord(humiditySensorRecord);

        airQualityDeviceRepository.save(airQualityDevice);
        airQualityDeviceRecordRepository.save(airQualityDeviceRecord);
        co2SensorRepository.save(co2Sensor);
        co2SensorRecordRepository.save(co2SensorRecord);
        temperatureSensorRepository.save(temperatureSensor);
        temperatureSensorRecordRepository.save(temperatureSensorRecord);
        humiditySensorRepository.save(humiditySensor);
        humiditySensorRecordRepository.save(humiditySensorRecord);
        return ResponseEntity.ok(airQualityDevice);
    }
    @PutMapping(value = "/room/{room_id:.*}/AirQuality/{airquality_id:.*}")
    public ResponseEntity<AirQualityDevice> chgAirQuality(@PathVariable Long room_id,
                                                          @PathVariable Long airquality_id,
                                                          @RequestParam Optional<Double> co2,
                                                          @RequestParam Optional<Double> humidity,
                                                          @RequestParam Optional<Double> temperature) {

        final Optional<Room> room = roomRepository.findById(room_id);
        final Optional<AirQualityDevice> aqd = room.get().getAirQualityDevices().stream()
                .filter(a -> a.getId().equals(airquality_id)).findFirst();

        if (room.isPresent() && aqd.isPresent()) {
            if (co2.isPresent()) {
                Co2SensorRecord co2r = new Co2SensorRecord();
                co2r.setTimestamp(LocalDateTime.now());
                co2r.setCo2Sensor(aqd.get().getCo2Sensor());
                co2r.setCo2(co2.get());
                co2SensorRecordRepository.save(co2r);
                aqd.get().getCo2Sensor().setCo2(co2.get());
            }

            if (humidity.isPresent()) {
                HumiditySensorRecord hsr= new HumiditySensorRecord();
                hsr.setTimestamp(LocalDateTime.now());
                hsr.setHumiditySensor(aqd.get().getHumiditySensor());
                hsr.setHumidity(humidity.get());
                humiditySensorRecordRepository.save(hsr);
                aqd.get().getHumiditySensor().setHumidity(humidity.get());

            }

            if (temperature.isPresent()) {
                TemperatureSensorRecord tsr= new TemperatureSensorRecord();
                tsr.setTimestamp(LocalDateTime.now());
                tsr.setTemperatureSensor(aqd.get().getTemperatureSensor());
                tsr.setTemperature(temperature.get());
                temperatureSensorRecordRepository.save(tsr);
                aqd.get().getTemperatureSensor().setTemperature(temperature.get());
            }
        }
        return ResponseEntity.ok(aqd.get());
    }

}