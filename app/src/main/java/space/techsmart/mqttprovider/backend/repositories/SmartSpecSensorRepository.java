package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.SmartSpecMetaSensor;
import space.techsmart.mqttprovider.backend.entities.SmartSpecSensor;

import java.util.List;

@Repository
public interface SmartSpecSensorRepository extends JpaRepository<SmartSpecSensor, Long> {

    List<SmartSpecSensor> findByDescriptionContainingIgnoreCase(String name);

    List<SmartSpecSensor> findAllBySmartSpecDatasetId(final Long smartSpecDatasetId);

}
