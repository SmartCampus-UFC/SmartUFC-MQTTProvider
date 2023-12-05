package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDataset;
import space.techsmart.mqttprovider.backend.repositories.SmartSpecDatasetRepository;
import space.techsmart.mqttprovider.backend.utils.Param;
import space.techsmart.mqttprovider.backend.utils.RandomDirName;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmartSpecDatasetService implements CrudListener<SmartSpecDataset> {

    private final SmartSpecDatasetRepository repository;

    @Override
    public Collection<SmartSpecDataset> findAll() {
        return repository.findAll();
    }

    @Override
    public SmartSpecDataset add(SmartSpecDataset smartSpecDataset) {
        smartSpecDataset.setCodePath(RandomDirName.generateRandomDirCode());
        smartSpecDataset.setStatus("NotAvailable");
        return repository.save(smartSpecDataset);
    }

    @Override
    public SmartSpecDataset update(SmartSpecDataset smartSpecDataset) {
        return repository.save(smartSpecDataset);
    }

    @Override
    public void delete(SmartSpecDataset smartSpecDataset) {
        repository.delete(smartSpecDataset);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<SmartSpecDataset> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public SmartSpecDataset findById(Long id) {
        return repository.findById(id).get();
    }

    /*public void generateProcessedDatasets (SmartSpecDataset smartSpecDataset, FileSystemStorageService fileSystemStorageService) {

        Path path = fileSystemStorageService.createGeneratedFilesLocation();
        File[] files = path.toFile().listFiles((FileFilter) new PrefixFileFilter(Param.SMARTSPEC_SENSOR_PREFIXFILE, IOCase.SENSITIVE));



    }*/

}
