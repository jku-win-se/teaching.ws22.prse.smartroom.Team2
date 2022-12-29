package at.jku.repository;

import at.jku.model.AirQualityDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirQualityDeviceRecordRepository extends JpaRepository<AirQualityDeviceRecord, Long> {

}
