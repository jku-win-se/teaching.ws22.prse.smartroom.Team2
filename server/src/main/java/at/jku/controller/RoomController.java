package at.jku.controller;

import at.jku.model.Room;
import at.jku.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoomController {

    private RoomRepository roomRepository;

    public RoomController(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
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
    public ResponseEntity<Room> updateRoom(@PathVariable Long room_id) {
        Room room = roomRepository.findById(room_id).orElse(null);
        roomRepository.delete(room);
        return ResponseEntity.ok(room);
    }

    @RequestMapping(value = "/rooms/{room_id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<Room> deleteRoom(@PathVariable Long room_id) {
        Room room = roomRepository.findById(room_id).orElse(null);
        roomRepository.delete(room);
        return ResponseEntity.ok(room);
    }


//    @PutMapping("/rooms/{room_id}")
//    public ResponseEntity<Room> updateRoom(Long room_id, @RequestParam Optional<String> name,
//                                           @RequestParam Optional<Integer> size) {
//        return ResponseEntity.ok(roomRepository.findById(room_id).orElse(null));
//    }
}

