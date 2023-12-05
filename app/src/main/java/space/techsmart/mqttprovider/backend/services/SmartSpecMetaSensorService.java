package space.techsmart.mqttprovider.backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.dataset.DatasetFactory;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDataset;
import space.techsmart.mqttprovider.backend.entities.SmartSpecMetaSensor;
import space.techsmart.mqttprovider.backend.repositories.SmartSpecMetaSensorRepository;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SmartSpecMetaSensorService implements CrudListener<SmartSpecMetaSensor> {

    private final SmartSpecMetaSensorRepository repository;

    @Override
    public Collection<SmartSpecMetaSensor> findAll() {
        return repository.findAll();
    }

    @Override
    public SmartSpecMetaSensor add(SmartSpecMetaSensor smartSpecMetaSensor) {
        return repository.save(smartSpecMetaSensor);
    }

    @Override
    public SmartSpecMetaSensor update(SmartSpecMetaSensor smartSpecMetaSensor) {
        return repository.save(smartSpecMetaSensor);
    }

    @Override
    public void delete(SmartSpecMetaSensor smartSpecMetaSensor) {
        repository.delete(smartSpecMetaSensor);
    }

    /*public void deleteAll(List<SmartSpecMetaSensor> smartSpecMetaSensorList) {
        repository.deleteAll(smartSpecMetaSensorList);
    }*/

    public int countAll() {
        return (int) repository.count();
    }

    public List<SmartSpecMetaSensor> findByDescriptionContainingIgnoreCase(String name) {
        return repository.findByDescriptionContainingIgnoreCase(name);
    }

    public SmartSpecMetaSensor findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SmartSpecMetaSensor> findAllBySmartSpecDatasetId(Long smartSpecDatasetId) {
        return repository.findAllBySmartSpecDatasetId(smartSpecDatasetId);
    }
    public void generateNewData(SmartSpecDataset smartSpecDataset,
                                FileSystemStorageService fileSystemStorageService) {
        // Removing old entities
        List<SmartSpecMetaSensor> entities = repository.findAllBySmartSpecDatasetId(smartSpecDataset.getId());
        if (entities.size()>0) repository.deleteAll(entities);
        // Load file
        fileSystemStorageService.generateTargetLocation("smartspec", smartSpecDataset.getCodePath());
        Path filePath = fileSystemStorageService.getFileNameLocation(Param.SMARTSPEC_METASENSORS_FILE);
        // Load json data and save in database
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<Map<String, Object>> sensors = objectMapper.readValue(filePath.toFile(), new TypeReference<>(){});
            DatasetFactory datasetFactory = new DatasetFactory();

            for (Map<String, Object> sensor : sensors) {

                // Save data in database
                SmartSpecMetaSensor entityToSave = new SmartSpecMetaSensor();
                entityToSave.setJsonId(Long.parseLong(String.valueOf(sensor.get("id"))));
                entityToSave.setDescription((String) sensor.get("description"));
                entityToSave.setSmartSpecDataset(smartSpecDataset);
                repository.save(entityToSave);

                String datasetFilePath = fileSystemStorageService.getGeneratedFilesLocation().getParent().toFile().getAbsolutePath()
                        + File.separator
                        + Param.SMARTSPEC_SENSOR_PREFIXFILE
                        + entityToSave.getJsonId()+".csv";

                datasetFactory.generateDeviceDataset(datasetFilePath, fileSystemStorageService.getGeneratedFilesLocation().toFile().getAbsolutePath(), entityToSave.getDescription());


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
