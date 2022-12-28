package at.jku.repository;

import at.jku.model.VentilatorRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentilatorRecordRepository extends JpaRepository<VentilatorRecord, Long> {

}
