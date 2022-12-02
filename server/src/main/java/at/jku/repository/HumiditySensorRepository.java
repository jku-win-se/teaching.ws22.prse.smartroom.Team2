package at.jku.repository;

import at.jku.model.HumiditySensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumiditySensorRepository extends JpaRepository<HumiditySensor, Long> {

}
