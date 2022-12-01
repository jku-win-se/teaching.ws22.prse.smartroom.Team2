package at.jku.repository;

import at.jku.model.DoorRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorRecordRepository extends JpaRepository<DoorRecord, Long> {

}
