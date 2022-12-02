package at.jku.repository;

import at.jku.model.TemperatureSensorRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureSensorRecordRepository extends JpaRepository<TemperatureSensorRecord, Long> {

}
