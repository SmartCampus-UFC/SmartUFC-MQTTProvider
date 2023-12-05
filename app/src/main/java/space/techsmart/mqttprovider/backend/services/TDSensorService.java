package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.TDSensor;
import space.techsmart.mqttprovider.backend.repositories.TDSensorRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TDSensorService implements CrudListener<TDSensor> {

    private final TDSensorRepository repository;


    @Override
    public Collection<TDSensor> findAll() {
        return repository.findAll();
    }

    @Override
    public TDSensor add(TDSensor tdSensor) {
        return repository.save(tdSensor);
    }

    @Override
    public TDSensor update(TDSensor tdSensor) {
        return repository.save(tdSensor);
    }

    @Override
    public void delete(TDSensor tdSensor) {
        repository.delete(tdSensor);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<TDSensor> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
