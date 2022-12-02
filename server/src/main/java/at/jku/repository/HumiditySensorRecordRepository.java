package at.jku.repository;

import at.jku.model.HumiditySensorRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumiditySensorRecordRepository extends JpaRepository<HumiditySensorRecord, Long> {

}
