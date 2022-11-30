package at.jku.repository;

import at.jku.model.Door;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorRepository extends JpaRepository<Door, Long> {

}
