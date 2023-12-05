package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.EDSensor;
import space.techsmart.mqttprovider.backend.repositories.EDSensorRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EDSensorService implements CrudListener<EDSensor> {

    private final EDSensorRepository repository;


    @Override
    public Collection<EDSensor> findAll() {
        return repository.findAll();
    }

    @Override
    public EDSensor add(EDSensor edSensor) {
        return repository.save(edSensor);
    }

    @Override
    public EDSensor update(EDSensor edSensor) {
        return repository.save(edSensor);
    }

    @Override
    public void delete(EDSensor edSensor) {
        repository.delete(edSensor);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<EDSensor> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
