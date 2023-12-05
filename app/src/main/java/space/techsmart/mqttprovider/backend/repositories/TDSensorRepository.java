package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.TDSensor;

import java.util.List;

@Repository
public interface TDSensorRepository extends JpaRepository<TDSensor, Long> {

    List<TDSensor> findByNameContainingIgnoreCase(String name);

}
