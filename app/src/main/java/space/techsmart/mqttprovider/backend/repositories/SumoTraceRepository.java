package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.SumoTrace;

import java.util.List;

@Repository
public interface SumoTraceRepository extends JpaRepository<SumoTrace, Long> {

    List<SumoTrace> findByFileNameContainingIgnoreCase(String name);

}
