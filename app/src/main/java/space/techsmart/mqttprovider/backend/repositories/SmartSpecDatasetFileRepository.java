package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDatasetFile;

import java.util.List;

@Repository
public interface SmartSpecDatasetFileRepository extends JpaRepository<SmartSpecDatasetFile, Long> {

    List<SmartSpecDatasetFile> findByFileNameContainingIgnoreCase(String name);

    List<SmartSpecDatasetFile> findAllBySmartSpecDatasetId(final Long smartSpecDatasetId);

}
