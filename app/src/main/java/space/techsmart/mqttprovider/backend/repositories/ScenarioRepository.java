package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.Scenario;

import java.util.List;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    List<Scenario> findByNameContainingIgnoreCase(String name);

}
