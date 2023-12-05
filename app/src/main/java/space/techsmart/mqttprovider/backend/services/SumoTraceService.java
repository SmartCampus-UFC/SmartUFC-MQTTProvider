package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.SumoTrace;
import space.techsmart.mqttprovider.backend.repositories.SumoTraceRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SumoTraceService implements CrudListener<SumoTrace> {

    private final SumoTraceRepository repository;

    @Override
    public Collection<SumoTrace> findAll() {
        return repository.findAll();
    }

    @Override
    public SumoTrace add(SumoTrace sumoTrace) {
        return repository.save(sumoTrace);
    }

    @Override
    public SumoTrace update(SumoTrace sumoTrace) {
        return repository.save(sumoTrace);
    }

    @Override
    public void delete(SumoTrace sumoTrace) {
        repository.delete(sumoTrace);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<SumoTrace> findByNameContainingIgnoreCase(String name) {
        return repository.findByFileNameContainingIgnoreCase(name);
    }

}
