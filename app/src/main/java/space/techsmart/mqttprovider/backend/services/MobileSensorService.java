package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.MobileSensor;
import space.techsmart.mqttprovider.backend.repositories.MobileSensorRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MobileSensorService implements CrudListener<MobileSensor> {

    private final MobileSensorRepository repository;


    @Override
    public Collection<MobileSensor> findAll() {
        return repository.findAll();
    }

    @Override
    public MobileSensor add(MobileSensor mobileSensor) {
        return repository.save(mobileSensor);
    }

    @Override
    public MobileSensor update(MobileSensor mobileSensor) {
        return repository.save(mobileSensor);
    }

    @Override
    public void delete(MobileSensor mobileSensor) {
        repository.delete(mobileSensor);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<MobileSensor> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
