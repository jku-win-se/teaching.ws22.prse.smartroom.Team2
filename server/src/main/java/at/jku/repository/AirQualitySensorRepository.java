package at.jku.repository;

import at.jku.model.AirQualitySensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirQualitySensorRepository extends JpaRepository<AirQualitySensor, Long> {

}
