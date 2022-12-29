package at.jku.repository;

import at.jku.model.AirQualityDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirQualityDeviceRepository extends JpaRepository<AirQualityDevice, Long> {

}
