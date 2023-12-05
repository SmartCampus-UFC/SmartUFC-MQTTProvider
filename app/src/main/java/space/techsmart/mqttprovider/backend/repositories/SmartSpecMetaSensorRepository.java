package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDatasetFile;
import space.techsmart.mqttprovider.backend.entities.SmartSpecMetaSensor;

import java.util.List;

@Repository
public interface SmartSpecMetaSensorRepository extends JpaRepository<SmartSpecMetaSensor, Long> {

    List<SmartSpecMetaSensor> findByDescriptionContainingIgnoreCase(String name);

    List<SmartSpecMetaSensor> findAllBySmartSpecDatasetId(final Long smartSpecDatasetId);

}
