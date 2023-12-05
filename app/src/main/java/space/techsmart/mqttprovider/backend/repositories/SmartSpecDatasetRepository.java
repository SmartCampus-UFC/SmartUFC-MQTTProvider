package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDataset;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmartSpecDatasetRepository extends JpaRepository<SmartSpecDataset, Long> {

    List<SmartSpecDataset> findByNameContainingIgnoreCase(String name);

}
