package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.Command;
import space.techsmart.mqttprovider.backend.repositories.CommandRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandService implements CrudListener<Command> {

    private final CommandRepository repository;


    @Override
    public Collection<Command> findAll() {
        return repository.findAll();
    }

    @Override
    public Command add(Command command) {
        return repository.save(command);
    }

    @Override
    public Command update(Command command) {
        return repository.save(command);
    }

    @Override
    public void delete(Command command) {
        repository.delete(command);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<Command> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
