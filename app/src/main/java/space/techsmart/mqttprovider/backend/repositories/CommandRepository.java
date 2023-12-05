package space.techsmart.mqttprovider.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.techsmart.mqttprovider.backend.entities.Command;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    List<Command> findByNameContainingIgnoreCase(String name);

}
