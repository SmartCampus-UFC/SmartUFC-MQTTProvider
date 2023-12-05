package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.SmartSpecSensor;
import space.techsmart.mqttprovider.backend.entities.SmartSpecSpace;

import java.util.List;

@Repository
public interface SmartSpecSpaceRepository extends JpaRepository<SmartSpecSpace, Long> {

    List<SmartSpecSpace> findByDescriptionContainingIgnoreCase(String name);

    List<SmartSpecSpace> findAllBySmartSpecDatasetId(final Long smartSpecDatasetId);

}
