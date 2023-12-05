package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.Actuator;
import space.techsmart.mqttprovider.backend.repositories.ActuatorRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActuatorService implements CrudListener<Actuator> {

    private final ActuatorRepository repository;

    @Override
    public Collection<Actuator> findAll() {
        return repository.findAll();
    }

    @Override
    public Actuator add(Actuator actuator) {
        return repository.save(actuator);
    }

    @Override
    public Actuator update(Actuator actuator) {
        return repository.save(actuator);
    }

    @Override
    public void delete(Actuator actuator) {
        repository.delete(actuator);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<Actuator> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
