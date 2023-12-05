package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.Actuator;

import java.util.List;

@Repository
public interface ActuatorRepository extends JpaRepository<Actuator, Long> {

    List<Actuator> findByNameContainingIgnoreCase(String name);

}
