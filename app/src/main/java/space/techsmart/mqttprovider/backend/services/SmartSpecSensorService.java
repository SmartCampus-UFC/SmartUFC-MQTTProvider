package space.techsmart.mqttprovider.backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDataset;
import space.techsmart.mqttprovider.backend.entities.SmartSpecSensor;
import space.techsmart.mqttprovider.backend.repositories.SmartSpecSensorRepository;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SmartSpecSensorService implements CrudListener<SmartSpecSensor> {

    private final SmartSpecSensorRepository repository;

    @Override
    public Collection<SmartSpecSensor> findAll() {
        return repository.findAll();
    }

    @Override
    public SmartSpecSensor add(SmartSpecSensor smartSpecSensor) {
        return repository.save(smartSpecSensor);
    }

    @Override
    public SmartSpecSensor update(SmartSpecSensor smartSpecSensor) {
        return repository.save(smartSpecSensor);
    }

    @Override
    public void delete(SmartSpecSensor smartSpecSensor) {
        repository.delete(smartSpecSensor);
    }

    public void deleteAll(List<SmartSpecSensor> smartSpecSensorList) {
        repository.deleteAll(smartSpecSensorList);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<SmartSpecSensor> findByDescriptionContainingIgnoreCase(String name) {
        return repository.findByDescriptionContainingIgnoreCase(name);
    }

    public SmartSpecSensor findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SmartSpecSensor> findAllBySmartSpecDatasetId(Long smartSpecDatasetId) {
        return repository.findAllBySmartSpecDatasetId(smartSpecDatasetId);
    }

    public void generateNewData(SmartSpecDataset smartSpecDataset,
                                SmartSpecMetaSensorService smartSpecMetaSensorService,
                                FileSystemStorageService fileSystemStorageService) {
        // Removing old entities
        List<SmartSpecSensor> entities = repository.findAllBySmartSpecDatasetId(smartSpecDataset.getId());
        if (entities.size()>0) repository.deleteAll(entities);

        // Load file
        //fileSystemStorageService.generateTargetLocation("smartspec", smartSpecDataset.getCodePath());
        Path filePath = fileSystemStorageService.getFileNameLocation(Param.SMARTSPEC_SENSORS_FILE);
        // Load json data and save in database
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<Long, List<Long>> ids = new HashMap<>();

        try {
            List<Map<String, Object>> sensors = objectMapper.readValue(filePath.toFile(), new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> sensor : sensors) {
                SmartSpecSensor entityToSave = new SmartSpecSensor();
                Long id = Long.parseLong(String.valueOf(sensor.get("id")));
                Long metasensorId = Long.parseLong(String.valueOf(sensor.get("metasensor-id")));

                if (!ids.containsKey(metasensorId)) ids.put(metasensorId, new ArrayList<>());
                else ids.get(metasensorId).add(id);

                entityToSave.setJsonId(id);
                entityToSave.setDescription((String) sensor.get("description"));
                entityToSave.setJsonMetaSensorId(metasensorId);
                entityToSave.setSmartSpecDataset(smartSpecDataset);
                repository.save(entityToSave);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
