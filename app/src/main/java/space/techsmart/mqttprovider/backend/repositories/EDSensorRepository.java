package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.EDSensor;

import java.util.List;

@Repository
public interface EDSensorRepository extends JpaRepository<EDSensor, Long> {

    List<EDSensor> findByNameContainingIgnoreCase(String name);

}
