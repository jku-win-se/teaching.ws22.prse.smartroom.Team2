package at.jku.repository;

import at.jku.model.AirQualitySensorRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirQualitySensorRecordRepository extends JpaRepository<AirQualitySensorRecord, Long> {

}
