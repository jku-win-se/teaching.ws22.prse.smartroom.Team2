package at.jku.repository;

import at.jku.model.TemperatureSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureSensorRepository extends JpaRepository<TemperatureSensor, Long> {

}
