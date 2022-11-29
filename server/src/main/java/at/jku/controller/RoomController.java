package at.jku.controller;

import at.jku.model.Room;
import at.jku.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {

    private RoomRepository roomRepository;

    public RoomController(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @PostMapping("/addroom")
    public ResponseEntity<Room> newProductionOrder(String name, int size) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final Room room = new Room(name, size);
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }


}
