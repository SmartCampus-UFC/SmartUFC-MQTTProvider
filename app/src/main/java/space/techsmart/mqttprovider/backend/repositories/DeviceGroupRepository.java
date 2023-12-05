package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.DeviceGroup;

import java.util.List;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

    List<DeviceGroup> findByNameContainingIgnoreCase(String name);

}
