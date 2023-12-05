package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.Scenario;
import space.techsmart.mqttprovider.backend.repositories.ScenarioRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioService implements CrudListener<Scenario> {

    private final ScenarioRepository repository;

    @Override
    public Collection<Scenario> findAll() {
        return repository.findAll();
    }

    @Override
    public Scenario add(Scenario scenario) {
        return repository.save(scenario);
    }

    @Override
    public Scenario update(Scenario scenario) {
        return repository.save(scenario);
    }

    @Override
    public void delete(Scenario scenario) {
        repository.delete(scenario);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<Scenario> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
