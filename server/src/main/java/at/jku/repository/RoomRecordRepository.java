package at.jku.repository;

import at.jku.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRecordRepository extends JpaRepository<Room, Long> {

}
