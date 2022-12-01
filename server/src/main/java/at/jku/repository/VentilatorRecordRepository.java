package at.jku.repository;

import at.jku.model.Ventilator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentilatorRecordRepository extends JpaRepository<Ventilator, Long> {

}
