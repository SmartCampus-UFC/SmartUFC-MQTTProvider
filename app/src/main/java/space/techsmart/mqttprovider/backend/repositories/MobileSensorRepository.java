package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.MobileSensor;

import java.util.List;

@Repository
public interface MobileSensorRepository extends JpaRepository<MobileSensor, Long> {

    List<MobileSensor> findByNameContainingIgnoreCase(String name);

}
