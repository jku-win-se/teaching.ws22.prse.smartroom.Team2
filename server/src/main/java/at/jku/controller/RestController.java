package at.jku.controller;

import at.jku.model.*;
import at.jku.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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

    /**
     * @return http response (json) with all rooms and properties
     */
    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    /**
     * add a room to program and database with optional name and size, id is program-managed
     *
     * @param name name of room
     * @param size size of room
     * @return the newly created room entity and its properties as http response (json)
     */
    @PostMapping("/rooms")
    public ResponseEntity<Room> addRoom(@RequestParam Optional<String> name,
                                        @RequestParam Optional<Integer> size) {
        final Room room = new Room();
        name.ifPresent(room::setName);
        size.ifPresent(room::setSize);
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }


    /**
     * get room by its id
     *
     * @param roomID room id
     * @return the room entity and its properties as http response (json)
     */

    @GetMapping(value = "/rooms/{roomID:.*}")
    public ResponseEntity<Room> getRoom(@PathVariable Long roomID) {
        return ResponseEntity.ok(roomRepository.findById(roomID).orElse(null));
    }

    /**
     * update room properties by id
     *
     * @param roomID room id
     * @param name   room name
     * @param size   room size
     * @return the updated room entity with all its properties as http response (json)
     */
    @PutMapping(value = "/rooms/{roomID:.*}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long roomID,
                                           @RequestParam Optional<String> name,
                                           @RequestParam Optional<Integer> size) {
        Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            name.ifPresent(s -> room.get().setName(s));
            size.ifPresent(integer -> room.get().setSize(integer));
            roomRepository.save(room.get());
        }
        return ResponseEntity.ok(room.orElse(null));
    }

    /**
     * delete a room by its id
     *
     * @param roomID room id
     * @return the deleted room entity as http response (json)
     */
    @DeleteMapping(value = "/rooms/{roomID:.*}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long roomID) {
        Optional<Room> room = roomRepository.findById(roomID);
        room.ifPresent(roomRepository::delete);
        return ResponseEntity.ok(room.orElse(null));
    }

    // ============================== PEOPLE IN ROOMS =====================================

    /**
     * get actual count of people in room (the latest database entry)
     *
     * @param roomID room id
     * @return peopleInRoom object as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/PeopleInRoom")
    public ResponseEntity<List<PeopleInRoom>> getPeopleInRoom(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        return ResponseEntity.ok(room.map(value -> new ArrayList<>(value.getPeopleInRooms())).orElse(null));
    }

    /**
     * set the number of peopleInRoom by room id
     *
     * @param roomID      room id
     * @param peopleCount number of people
     * @return created peopleInRoom object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/PeopleInRoom")
    public ResponseEntity<PeopleInRoom> chgPeopleInRoom(@PathVariable Long roomID,
                                                        @RequestParam Optional<Integer> peopleCount) {
        final Optional<Room> room = roomRepository.findById(roomID);
        PeopleInRoom pir = new PeopleInRoom();
        if (room.isPresent() && peopleCount.isPresent()) {
            pir.setTimestamp(LocalDateTime.now());
            pir.setRoom(room.get());
            pir.setNumPeopleInRoom(peopleCount.get());
            room.get().addPeopleInRoom(pir);
            peopleInRoomRepository.save(pir);

            // Automation rule: Turn off running devices if the room is empty
            if (peopleCount.get() <= 0) {
                room.get().getLightSources().forEach(l -> {
                    LightSourceRecord lr = new LightSourceRecord();
                    lr.setLightSource(l);
                    lr.setTimestamp(LocalDateTime.now());
                    lr.setState(false);
                    l.addLightSourceRecord(lr);
                    lightSourceRecordRepository.save(lr);
                });
                room.get().getVentilators().forEach(v -> {
                    VentilatorRecord vr = new VentilatorRecord();
                    vr.setVentilator(v);
                    vr.setTimestamp(LocalDateTime.now());
                    vr.setState(false);
                    v.addVentilatorRecord(vr);
                    ventilatorRecordRepository.save(vr);
                });
                room.get().getAirQualityDevices().forEach(a -> {
                    AirQualityDeviceRecord ar = new AirQualityDeviceRecord();
                    ar.setAirQualityDevice(a);
                    ar.setTimestamp(LocalDateTime.now());
                    ar.setState(false);
                    a.addAirQualityDeviceRecord(ar);
                    airQualityDeviceRecordRepository.save(ar);
                });
            }
        }
        return ResponseEntity.ok(room.map(Room::getNumPeopleInRoom).orElse(null));
    }

    // ============================== LIGHTS =====================================

    /**
     * get all lightSources of a room by its id
     *
     * @param roomID room id
     * @return all lightSource objects of the given room as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/lights")
    public ResponseEntity<List<LightSource>> getRoomLights(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        return ResponseEntity.ok(room.map(value -> new ArrayList<>(value.getLightSources())).orElse(null));
    }

    /**
     * add a new lightSource to a given room by its id
     *
     * @param roomID room id
     * @param name   light source name
     * @return the newly created lightSource object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID}/lights")
    public ResponseEntity<LightSource> addLightSource(@PathVariable Long roomID,
                                                      @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(roomID);
        final LightSource ls = new LightSource();
        name.ifPresent(ls::setName);
        if (room.isPresent()) {
            room.get().addLightSource(ls);
            ls.setRoom(room.get());
            lightSourceRepository.save(ls);
        }
        return ResponseEntity.ok(ls);
    }

    /**
     * get a specific lightSource of a specific room by ids of those
     *
     * @param roomID  room id
     * @param lightID light id
     * @return the lightSource object as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/lights/{lightID:.*}")
    public ResponseEntity<LightSource> getLightSource(@PathVariable Long roomID,
                                                      @PathVariable Long lightID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<LightSource> ls = room.get()
                    .getLightSources().stream().filter(l -> l.getId().equals(lightID)).findFirst();
            return ResponseEntity.ok(ls.orElse(null));
        }
        return ResponseEntity.ok(null);
    }

    /**
     * update the name of a lightSource by its id and the room id which it is located in
     *
     * @param roomID  room id
     * @param lightID light id
     * @param name    light source name
     * @return the updated lightSource object as http response (json)
     */
    @PatchMapping(value = "/rooms/{roomID:.*}/lights/{lightID:.*}")
    public ResponseEntity<LightSource> updateLightSource(@PathVariable Long roomID,
                                                         @PathVariable Long lightID,
                                                         @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<LightSource> ls = room.get().getLightSources().stream().filter(l -> l.getId().equals(lightID)).findFirst();
            if (ls.isPresent() && name.isPresent()) {
                ls.get().setName(name.get());
                lightSourceRepository.save(ls.get());
                return ResponseEntity.ok(ls.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * get the activation status (on/off = true/false) of a lightSource by its containing room id and its own id
     *
     * @param roomID  room id
     * @param lightID light source id
     * @return the lightSource object as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/lights/{lightID:.*}/Activation")
    public ResponseEntity<LightSource> getLightSourceStatus(@PathVariable Long roomID,
                                                            @PathVariable Long lightID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<LightSource> ls = room.get()
                    .getLightSources().stream()
                    .filter(l -> l.getId().equals(lightID)).findFirst();
            return ResponseEntity.ok(ls.orElse(null));
        }
        return ResponseEntity.ok(null);
    }

    /**
     * set a lightSources status (on/off = true/false) by its containing room id, its own id and the turnon = true/false parameter
     *
     * @param roomID  room id
     * @param lightID light source id
     * @param turnon  (on/off) = (true/false)
     * @return the updated lightSource object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/lights/{lightID:.*}/Activation")
    public ResponseEntity<LightSource> chgLightSourceStatus(@PathVariable Long roomID,
                                                            @PathVariable Long lightID,
                                                            @RequestParam Optional<Boolean> turnon) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<LightSource> ls = room.get()
                    .getLightSources().stream()
                    .filter(l -> l.getId().equals(lightID)).findFirst();
            if (ls.isPresent() && turnon.isPresent()) {
                LightSourceRecord lsr = new LightSourceRecord();
                lsr.setTimestamp(LocalDateTime.now());
                lsr.setState(turnon.get());
                lsr.setLightSource(ls.get());
                ls.get().addLightSourceRecord(lsr);
                lightSourceRecordRepository.save(lsr);
                return ResponseEntity.ok(ls.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * delete a lightSource of a room by room id and a lightSource id which is located in the room
     *
     * @param roomID  room id
     * @param lightID light source id
     * @return the deleted lightSource object as http response (json)
     */

    @DeleteMapping(value = "/rooms/{roomID:.*}/lights/{lightID:.*}")
    public ResponseEntity<LightSource> deleteLightSource(@PathVariable Long roomID,
                                                         @PathVariable Long lightID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<LightSource> ls = room.get().getLightSources().stream()
                    .filter(l -> l.getId().equals(lightID)).findFirst();
            if (ls.isPresent()) {
                room.get().getLightSources().remove(ls.get());
                lightSourceRepository.delete(ls.get());
                return ResponseEntity.ok(ls.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * set the color and brightness of a lightSource by its containing room id and its own id
     *
     * @param roomID     room id
     * @param lightID    light source id
     * @param hex        color
     * @param brightness brightness
     * @return the updated lightSource object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/lights/{lightID:.*}/SetColor")
    public ResponseEntity<LightSource> chgLightSourceColor(@PathVariable Long roomID,
                                                           @PathVariable Long lightID,
                                                           @RequestParam Optional<String> hex,
                                                           @RequestParam Optional<Integer> brightness) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<LightSource> ls = room.get()
                    .getLightSources().stream()
                    .filter(l -> l.getId().equals(lightID)).findFirst();
            if (ls.isPresent()) {
                hex.ifPresent(s -> ls.get().setHex(s));
                brightness.ifPresent(integer -> ls.get().setBrightness(integer));
                lightSourceRepository.save(ls.get());
                return ResponseEntity.ok(ls.get());
            }
        }
        return ResponseEntity.ok(null);
    }


    // ============================== VENTILATORS =====================================

    /**
     * get all ventilator objects by its containing room
     *
     * @param roomID room id
     * @return all ventilator objects contained in the specified room_id as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/ventilators")
    public ResponseEntity<ArrayList<Ventilator>> getVentilators(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        return room.map(value -> ResponseEntity.ok(new ArrayList<>(value.getVentilators()))).orElseGet(() -> ResponseEntity.ok(null));
    }

    /**
     * add a new ventilator to the specified room
     *
     * @param roomID room id
     * @param name   ventilator name
     * @return the newly created ventilator object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID}/ventilators")
    public ResponseEntity<Ventilator> addVentilator(@PathVariable Long roomID,
                                                    @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(roomID);
        final Ventilator ventilator = new Ventilator();

        if (room.isPresent()) {
            room.get().addVentilator(ventilator);
            name.ifPresent(ventilator::setName);
            ventilator.setRoom(room.get());
            ventilatorRepository.save(ventilator);
        }
        return ResponseEntity.ok(ventilator);
    }

    /**
     * get ventilator by its containing rooms id and own id
     *
     * @param roomID       room id
     * @param ventilatorID ventilator id
     * @return the ventilator object as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/ventilators/{ventilatorID:.*}")
    public ResponseEntity<Ventilator> getVentilator(@PathVariable Long roomID,
                                                    @PathVariable Long ventilatorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilatorID)).findFirst();
            return ResponseEntity.ok(vent.orElse(null));
        }
        return ResponseEntity.ok(null);
    }

    /**
     * delete a ventilator by its containing rooms id and its own id
     *
     * @param roomID       room id
     * @param ventilatorID ventilator id
     * @return the deleted ventilator object as http response (json)
     */
    @DeleteMapping(value = "/rooms/{roomID:.*}/ventilators/{ventilatorID:.*}")
    public ResponseEntity<Ventilator> deleteVentilator(@PathVariable Long roomID,
                                                       @PathVariable Long ventilatorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Ventilator> vent = room.get().getVentilators().stream()
                    .filter(l -> l.getId().equals(ventilatorID)).findFirst();
            if (vent.isPresent()) {
                room.get().getVentilators().remove(vent.get());
                ventilatorRepository.delete(vent.get());
                return ResponseEntity.ok(vent.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * update a ventilators name by its containing room id and its own id
     *
     * @param roomID       room id
     * @param ventilatorID ventilator id
     * @param name         ventilator name
     * @return the updated ventilator object as http response (json)
     */
    @PatchMapping(value = "/rooms/{roomID:.*}/ventilators/{ventilatorID:.*}")
    public ResponseEntity<Ventilator> updateVentilator(@PathVariable Long roomID,
                                                       @PathVariable Long ventilatorID,
                                                       @RequestParam String name) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilatorID)).findFirst();
            vent.ifPresent(ventilator -> ventilator.setName(name));
            if (vent.isPresent()) {
                ventilatorRepository.save(vent.get());
                return ResponseEntity.ok(vent.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * change the operational state (on/off = true/false) by its containing room id and its own id
     *
     * @param roomID       room id
     * @param ventilatorID ventilator id
     * @param turnon       (on/off) = (true/false)
     * @return the updated ventilator object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/ventilators/{ventilatorID:.*}/Operations")
    public ResponseEntity<Ventilator> postVentilatorState(@PathVariable Long roomID,
                                                          @PathVariable Long ventilatorID,
                                                          @RequestParam Optional<Boolean> turnon) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Ventilator> vent = room.get().getVentilators().stream().filter(l -> l.getId().equals(ventilatorID)).findFirst();
            if (vent.isPresent() && turnon.isPresent()) {
                VentilatorRecord vr = new VentilatorRecord();
                vr.setVentilator(vent.get());
                vr.setTimestamp(LocalDateTime.now());
                vr.setState(turnon.get());
                vent.get().addVentilatorRecord(vr);
                ventilatorRecordRepository.save(vr);
                return ResponseEntity.ok(vent.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * get the actual ventilator status by its containing room id and the ventilator id
     *
     * @param roomID       room id
     * @param ventilatorID ventilator id
     * @return the latest ventilator record for the specified ventilator as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/ventilators/{ventilatorID:.*}/Activation")
    public ResponseEntity<VentilatorRecord> getVentilatorState(@PathVariable Long roomID,
                                                               @PathVariable Long ventilatorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Ventilator> vent = room.get().getVentilators()
                    .stream().filter(l -> l.getId().equals(ventilatorID)).findFirst();
            if (vent.isPresent()) {
                final Optional<VentilatorRecord> vr = vent.get().getLatestVentilatorRecord();
                return ResponseEntity.ok(vr.orElse(null));
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * change the operational state of a ventilator by its containing room id and the ventilator id
     * if it's running it will be off afterwards, if it's turned off, it will be turned on afterwards
     *
     * @param roomID       room id
     * @param ventilatorID ventilator id
     * @return the updated ventilator object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/ventilators/{ventilatorID:.*}/Activation")
    public ResponseEntity<Ventilator> activateVentilator(@PathVariable Long roomID,
                                                         @PathVariable Long ventilatorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Ventilator> vent = room.get().getVentilators()
                    .stream().filter(l -> l.getId().equals(ventilatorID)).findFirst();
            if (vent.isPresent()) {
                VentilatorRecord vr = new VentilatorRecord();
                vr.setVentilator(vent.get());
                vr.setTimestamp(LocalDateTime.now());
                vr.setState(!(vent.get().getState()));
                vent.get().addVentilatorRecord(vr);
                ventilatorRecordRepository.save(vr);
                return ResponseEntity.ok(vent.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    // ============================== WINDOWS =====================================

    /**
     * get all windows of a specified room by room id
     *
     * @param roomID room id
     * @return all window objects of the specified room as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/windows")
    public ResponseEntity<List<Windo>> getWindows(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        return room.<ResponseEntity<List<Windo>>>map(value -> ResponseEntity.ok(new ArrayList<>(value.getWindows()))).orElseGet(() -> ResponseEntity.ok(null));
    }

    /**
     * add a new window to a room by the rooms id
     *
     * @param roomID room id
     * @return the newly created window object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/windows")
    public ResponseEntity<Windo> addWindow(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        final Windo window = new Windo();
        if (room.isPresent()) {
            room.get().addWindow(window);
            window.setRoom(room.orElse(null));
            windoRepository.save(window);
        }
        return ResponseEntity.ok(window);
    }

    /**
     * get the specified window object by room id and its own id
     *
     * @param roomID   room id
     * @param windowID ventilator id
     * @return the window object as http response (json) or an empty json
     */
    @GetMapping(value = "/rooms/{roomID:.*}/windows/{windowID:.*}")
    public ResponseEntity<Windo> getWindow(@PathVariable Long roomID,
                                           @PathVariable Long windowID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            Optional<Windo> window = room.get().getWindows().stream().filter(l -> l.getId().equals(windowID)).findFirst();
            if (window.isPresent()) {
                return ResponseEntity.ok(window.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * update a specified window by its id and the containing rooms id
     *
     * @param roomID   room id
     * @param windowID ventilator id
     * @return the updated window object as http response (json) or an empty json
     */
    @PutMapping(value = "/rooms/{roomID:.*}/windows/{windowID:.*}")
    public ResponseEntity<Windo> updateWindow(@PathVariable Long roomID,
                                              @PathVariable Long windowID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Windo> window = room.get().getWindows().stream().filter(l -> l.getId().equals(windowID)).findFirst();
            return ResponseEntity.ok(window.orElse(null));
        }
        return ResponseEntity.ok(null);
    }

    /**
     * delete a window by its containing room id and its own id
     *
     * @param roomID   room id
     * @param windowID ventilator id
     * @return the deleted window object as http response (json)
     */
    @DeleteMapping(value = "/rooms/{roomID:.*}/windows/{windowID:.*}")
    public ResponseEntity<Windo> deleteWindow(@PathVariable Long roomID,
                                              @PathVariable Long windowID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Windo> window = room.get().getWindows().stream()
                    .filter(l -> l.getId().equals(windowID)).findFirst();
            if (window.isPresent()) {
                room.get().getWindows().remove(window.get());
                windoRepository.delete(window.get());
                return ResponseEntity.ok(window.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * get the actual state (open/close = true/false) of a specified window by its containing room id and window id
     *
     * @param roomID   room id
     * @param windowID ventilator id
     * @return the latest change entry of the window (windowRecord) as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/windows/{windowID:.*}/Open")
    public ResponseEntity<WindowRecord> getWindowState(@PathVariable Long roomID,
                                                       @PathVariable Long windowID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Windo> win = room.get().getWindows().stream().filter(l -> l.getId().equals(windowID)).findFirst();
            if (win.isPresent()) {
                final Optional<WindowRecord> wr = win.get().getLatestWindowRecord();
                if (wr.isPresent()) {
                    return ResponseEntity.ok(wr.get());
                }
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * change the actual state of a window by its containing room id and its own id
     * if it was closed it afterwards open, if it's open, it's afterwards closed
     *
     * @param roomID   room id
     * @param windowID ventilator id
     * @return the updated window object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/windows/{windowID:.*}/Open")
    public ResponseEntity<Windo> postWindowState(@PathVariable Long roomID,
                                                 @PathVariable Long windowID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Windo> win = room.get().getWindows().stream().filter(l -> l.getId().equals(windowID)).findFirst();
            if (win.isPresent()) {
                final WindowRecord wr = new WindowRecord();
                wr.setWindow(win.get());
                wr.setTimestamp(LocalDateTime.now());
                wr.setState(!(win.get().getState()));
                win.get().addWindowRecord(wr);
                windowRecordRepository.save(wr);
                return ResponseEntity.ok(win.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    // ============================== DOORS =====================================

    /**
     * get all doors of a room by room id
     *
     * @param roomID room id
     * @return the door objects contained by the room as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/doors")
    public ResponseEntity<List<Door>> getDoors(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        return ResponseEntity.ok(room.map(value -> new ArrayList<>(value.getDoors())).orElse(null));
    }

    /**
     * add door to room
     *
     * @param roomID room id
     * @param name   new door name
     * @return the newly created door object as http response (json)
     */
    @PutMapping(value = "/rooms/{roomID:.*}/doors")
    public ResponseEntity<Door> addDoor(@PathVariable Long roomID,
                                        @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(roomID);
        final Door door = new Door();
        if (room.isPresent()) {
            name.ifPresent(door::setName);
            room.get().addDoor(door);
            door.addRoom(room.get());
            doorRepository.save(door);
        }
        return ResponseEntity.ok(door);
    }

    /**
     * get a specific door object by its containing room id and the door id
     *
     * @param roomID room id
     * @param doorID door id
     * @return the requested door object as http response (json)
     */
    @GetMapping(value = "/rooms/{roomID:.*}/doors/{doorID:.*}")
    public ResponseEntity<Door> getDoor(@PathVariable Long roomID,
                                        @PathVariable Long doorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Door> ls = room.get().getDoors().stream()
                    .filter(l -> l.getId().equals(doorID)).findFirst();
            if (ls.isPresent()) {
                return ResponseEntity.ok(ls.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * change name of door
     *
     * @param roomID room id
     * @param doorID door id
     * @param name   new name
     * @return the updated door object as http response (json)
     */
    @PutMapping(value = "/rooms/{roomID:.*}/doors/{doorID:.*}")
    public ResponseEntity<Door> updateDoor(@PathVariable Long roomID,
                                           @PathVariable Long doorID,
                                           @RequestParam Optional<String> name) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Door> door = room.get().getDoors()
                    .stream().filter(l -> l.getId().equals(doorID)).findFirst();
            if (door.isPresent() && name.isPresent()) {
                door.get().setName(name.get());
                doorRepository.save(door.get());
                return ResponseEntity.ok(door.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * delete door by its containing room id and its own id
     *
     * @param roomID room id
     * @param doorID door id
     * @return the deleted door object as http response (json)
     */
    @DeleteMapping(value = "/rooms/{roomID:.*}/doors/{doorID:.*}")
    public ResponseEntity<Door> deleteDoor(@PathVariable Long roomID,
                                           @PathVariable Long doorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Door> d = room.get().getDoors().stream()
                    .filter(l -> l.getId().equals(doorID)).findFirst();
            if (d.isPresent()) {
                room.get().getDoors().remove(d.get());
                doorRepository.delete(d.get());
                return ResponseEntity.ok(d.get());
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * get actual door status (opened/closed = true/false)
     *
     * @param roomID room id
     * @param doorID door id
     * @return the latest door record object as http response (json)
     */

    @GetMapping(value = "/rooms/{roomID:.*}/doors/{doorID:.*}/Open")
    public ResponseEntity<DoorRecord> getDoorState(@PathVariable Long roomID,
                                                   @PathVariable Long doorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Door> door = room.get().getDoors()
                    .stream().filter(l -> l.getId().equals(doorID)).findFirst();
            if (door.isPresent()) {
                final Optional<DoorRecord> dr = door.get().getLatestDoorRecord();
                if (dr.isPresent()) {
                    return ResponseEntity.ok(dr.get());
                }
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * update door state (opened/closed = true/false) if
     * When it's opened, its closed afterwards and vice versa
     *
     * @param roomID door id
     * @param doorID room id
     * @return the updated door object as http response (json)
     */
    @PostMapping(value = "/rooms/{roomID:.*}/doors/{doorID:.*}/Open")
    public ResponseEntity<Door> postDoorState(@PathVariable Long roomID,
                                              @PathVariable Long doorID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<Door> door = room.get().getDoors().stream().filter(l -> l.getId().equals(doorID)).findFirst();
            if (door.isPresent()) {
                final DoorRecord dr = new DoorRecord();
                dr.setDoor(door.get());
                dr.setTimestamp(LocalDateTime.now());
                dr.setState(!(door.get().getState()));
                door.get().addDoorRecord(dr);
                doorRecordRepository.save(dr);
            }
        }
        return ResponseEntity.ok(null);
    }

    // ============================== AIR-QUALITY =====================================

    /**
     * get all air quality devices and their properties of a specific room by room id
     *
     * @param roomID room id
     * @return a list of air quality devices of the specified room as http response (json)
     */
    @GetMapping(value = "/room/{roomID:.*}/AirQuality")
    public ResponseEntity<ArrayList<AirQualityDevice>> getAirQuality(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        return room.map(value -> ResponseEntity.ok(new ArrayList<>(value.getAirQualityDevices()))).orElseGet(() -> ResponseEntity.ok(null));
    }

    /**
     * get latest temperature record (actual room temperature)
     *
     * @param roomID room id
     * @return the latest temperature entry of a sensor as http response (json)
     */
    @GetMapping(value = "/room/{roomID:.*}/AirQuality/temperature")
    public ResponseEntity<TemperatureSensorRecord> getAirQualityTemperature(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            Optional<TemperatureSensorRecord> tsr =
                    room.map(value -> value.getAirQualityDevices().stream()
                            .map(aqd -> aqd.getTemperatureSensor().getLatestTemperatureSensorRecord())
                            .max(Comparator.comparing(t -> t.map(TemperatureSensorRecord::getTimestamp).get())).get().get());
            return ResponseEntity.ok(tsr.orElse(null));
        }
        return ResponseEntity.ok(null);
    }

    /**
     * get latest humidity record (actual room temperature)
     *
     * @param roomID room id
     * @return the latest humidity entry of a sensor as http response (json)
     */
    @GetMapping(value = "/room/{roomID:.*}/AirQuality/humidity")
    public ResponseEntity<HumiditySensorRecord> getAirQualityHumidity(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            Optional<HumiditySensorRecord> hsr =
                    room.map(value -> value.getAirQualityDevices().stream()
                            .map(aqd -> aqd.getHumiditySensor().getLatestHumiditySensorRecord())
                            .max(Comparator.comparing(t -> t.map(HumiditySensorRecord::getTimestamp).get())).get().get());
            return ResponseEntity.ok(hsr.orElse(null));
        }
        return ResponseEntity.ok(null);
    }

    /**
     * get latest co2 record (actual room temperature)
     *
     * @param roomID room id
     * @return the co2 temperature entry of a sensor as http response (json)
     */
    @GetMapping(value = "/room/{roomID:.*}/AirQuality/co2")
    public ResponseEntity<Co2SensorRecord> getAirQualityCo2(@PathVariable Long roomID) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            Optional<Co2SensorRecord> csr =
                    room.map(value -> value.getAirQualityDevices().stream()
                            .map(aqd -> aqd.getCo2Sensor().getLatestCo2SensorRecord())
                            .max(Comparator.comparing(t -> t.map(Co2SensorRecord::getTimestamp).get())).get().get());
            return ResponseEntity.ok(csr.orElse(null));
        }
        return ResponseEntity.ok(null);
    }

    /**
     * create new air quality device for a specific room with all properties and sensors
     *
     * @param roomID      room id
     * @param co2         initial co2 value
     * @param humidity    initial humidity value
     * @param temperature initial temperature
     * @return the newly created air quality device object as http response (json)
     */
    @PostMapping(value = "/room/AirQuality")
    public ResponseEntity<AirQualityDevice> addAirQuality(@RequestParam Long roomID,
                                                          @RequestParam Optional<Double> co2,
                                                          @RequestParam Optional<Double> humidity,
                                                          @RequestParam Optional<Double> temperature) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isEmpty()) {
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
        co2.ifPresent(co2SensorRecord::setCo2);

        temperatureSensorRecord.setTemperatureSensor(temperatureSensor);
        temperatureSensorRecord.setTimestamp(LocalDateTime.now());
        temperature.ifPresent(temperatureSensorRecord::setTemperature);

        humiditySensorRecord.setHumiditySensor(humiditySensor);
        humiditySensorRecord.setTimestamp(LocalDateTime.now());
        humidity.ifPresent(humiditySensorRecord::setHumidity);

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

        // Automation Rule: Unlock all doors if temp > 70
        if (temperature.isPresent() && temperature.get() > 70) {
            // open doors
            room.get().getDoors().stream().forEach(d -> {
                DoorRecord doorRecord = new DoorRecord();
                doorRecord.setDoor(d);
                doorRecord.setState(true);
                doorRecord.setTimestamp(LocalDateTime.now());
                d.addDoorRecord(doorRecord);
                doorRecordRepository.save(doorRecord);
                d.open();
            });
        }

        // Automation rule: Open window + activate fan if co2 values are > 1000 parts per million (ppm)
        if (co2.isPresent() && co2.get() > 1000) {
            room.get().getWindows().stream().forEach(w -> {
                WindowRecord wr = new WindowRecord();
                wr.setWindow(w);
                wr.setTimestamp(LocalDateTime.now());
                wr.setState(true);
                w.addWindowRecord(wr);
                windowRecordRepository.save(wr);
            });
            room.get().getVentilators().stream().forEach(v -> {
                VentilatorRecord vr = new VentilatorRecord();
                vr.setVentilator(v);
                vr.setTimestamp(LocalDateTime.now());
                vr.setState(true);
                v.addVentilatorRecord(vr);
                ventilatorRecordRepository.save(vr);
            });
        }

        return ResponseEntity.ok(airQualityDevice);
    }

    /**
     * update co2 and/or humidity and/or temperature of a specific air quality device by room id and air quality device id
     *
     * @param roomID       room id
     * @param airQualityID air quality device id
     * @param co2          new co2 value
     * @param humidity     new humidity value
     * @param temperature  new temperature value
     * @return the updated air quality device object as http response (json)
     */
    @PutMapping(value = "/room/{roomID:.*}/AirQuality/{airQualityID:.*}")
    public ResponseEntity<AirQualityDevice> chgAirQuality(@PathVariable Long roomID,
                                                          @PathVariable Long airQualityID,
                                                          @RequestParam Optional<Double> co2,
                                                          @RequestParam Optional<Double> humidity,
                                                          @RequestParam Optional<Double> temperature) {
        final Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            final Optional<AirQualityDevice> aqd = room.get().getAirQualityDevices().stream()
                    .filter(a -> a.getId().equals(airQualityID)).findFirst();
            if (aqd.isPresent()) {
                if (co2.isPresent()) {
                    Co2SensorRecord co2r = new Co2SensorRecord();
                    co2r.setTimestamp(LocalDateTime.now());
                    co2r.setCo2Sensor(aqd.get().getCo2Sensor());
                    co2r.setCo2(co2.get());
                    co2SensorRecordRepository.save(co2r);
                    aqd.get().getCo2Sensor().setCo2(co2.get());

                    // Automation rule: Open window + activate fan if co2 values are > 1000 parts per million (ppm)
                    if (co2.get() > 1000) {
                        room.get().getWindows().stream().forEach(w -> {
                            WindowRecord wr = new WindowRecord();
                            wr.setWindow(w);
                            wr.setTimestamp(LocalDateTime.now());
                            wr.setState(true);
                            w.addWindowRecord(wr);
                            windowRecordRepository.save(wr);
                        });
                        room.get().getVentilators().stream().forEach(v -> {
                            VentilatorRecord vr = new VentilatorRecord();
                            vr.setVentilator(v);
                            vr.setTimestamp(LocalDateTime.now());
                            vr.setState(true);
                            v.addVentilatorRecord(vr);
                            ventilatorRecordRepository.save(vr);
                        });
                    }
                }

                if (humidity.isPresent()) {
                    HumiditySensorRecord hsr = new HumiditySensorRecord();
                    hsr.setTimestamp(LocalDateTime.now());
                    hsr.setHumiditySensor(aqd.get().getHumiditySensor());
                    hsr.setHumidity(humidity.get());
                    humiditySensorRecordRepository.save(hsr);
                    aqd.get().getHumiditySensor().setHumidity(humidity.get());
                }

                if (temperature.isPresent()) {
                    TemperatureSensorRecord tsr = new TemperatureSensorRecord();
                    tsr.setTimestamp(LocalDateTime.now());
                    tsr.setTemperatureSensor(aqd.get().getTemperatureSensor());
                    tsr.setTemperature(temperature.get());
                    temperatureSensorRecordRepository.save(tsr);
                    aqd.get().getTemperatureSensor().setTemperature(temperature.get());

                    // Automation Rule: Unlock all doors if temp > 70
                    if (temperature.get() > 70) {
                        // open doors
                        room.get().getDoors().stream().forEach(d -> {
                            DoorRecord doorRecord = new DoorRecord();
                            doorRecord.setDoor(d);
                            doorRecord.setState(true);
                            doorRecord.setTimestamp(LocalDateTime.now());
                            d.addDoorRecord(doorRecord);
                            doorRecordRepository.save(doorRecord);
                            d.open();
                        });
                    }
                }
                return ResponseEntity.ok(aqd.get());
            }
        }
        return ResponseEntity.ok(null);
    }
}