package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDatasetFile;
import space.techsmart.mqttprovider.backend.repositories.SmartSpecDatasetFileRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmartSpecDatasetFileService implements CrudListener<SmartSpecDatasetFile> {

    private final SmartSpecDatasetFileRepository repository;

    @Override
    public Collection<SmartSpecDatasetFile> findAll() {
        return repository.findAll();
    }

    @Override
    public SmartSpecDatasetFile add(SmartSpecDatasetFile smartSpecDatasetFile) {
        return repository.save(smartSpecDatasetFile);
    }

    @Override
    public SmartSpecDatasetFile update(SmartSpecDatasetFile smartSpecDatasetFile) {
        return repository.save(smartSpecDatasetFile);
    }

    @Override
    public void delete(SmartSpecDatasetFile smartSpecDatasetFile) {
        repository.delete(smartSpecDatasetFile);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<SmartSpecDatasetFile> findByNameContainingIgnoreCase(String name) {
        return repository.findByFileNameContainingIgnoreCase(name);
    }

    public List<SmartSpecDatasetFile> findAllBySmartSpecDatasetId(Long smartSpecDatasetId) {
        return repository.findAllBySmartSpecDatasetId(smartSpecDatasetId);
    }

}

