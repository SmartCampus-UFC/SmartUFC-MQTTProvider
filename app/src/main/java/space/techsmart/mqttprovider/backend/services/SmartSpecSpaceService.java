package space.techsmart.mqttprovider.backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.dataset.DatasetFactory;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDataset;
import space.techsmart.mqttprovider.backend.entities.SmartSpecSpace;
import space.techsmart.mqttprovider.backend.repositories.SmartSpecSpaceRepository;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmartSpecSpaceService implements CrudListener<SmartSpecSpace> {

    private final SmartSpecSpaceRepository repository;

    @Override
    public Collection<SmartSpecSpace> findAll() {
        return repository.findAll();
    }

    @Override
    public SmartSpecSpace add(SmartSpecSpace smartSpecSpace) {
        return repository.save(smartSpecSpace);
    }

    @Override
    public SmartSpecSpace update(SmartSpecSpace smartSpecSpace) {
        return repository.save(smartSpecSpace);
    }

    @Override
    public void delete(SmartSpecSpace smartSpecSpace) {
        repository.delete(smartSpecSpace);
    }

    public void deleteAll(List<SmartSpecSpace> smartSpecSpaceList) {
        repository.deleteAll(smartSpecSpaceList);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<SmartSpecSpace> findByDescriptionContainingIgnoreCase(String name) {
        return repository.findByDescriptionContainingIgnoreCase(name);
    }

    public SmartSpecSpace findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SmartSpecSpace> findAllBySmartSpecDatasetId(Long smartSpecDatasetId) {
        return repository.findAllBySmartSpecDatasetId(smartSpecDatasetId);
    }

    public void generateNewData(SmartSpecDataset smartSpecDataset,
                                SmartSpecDatasetService smartSpecDatasetService,
                                FileSystemStorageService fileSystemStorageService) {
        // Removing old entities
        List<SmartSpecSpace> entities = repository.findAllBySmartSpecDatasetId(smartSpecDataset.getId());
        if (entities.size()>0) repository.deleteAll(entities);
        // Load file
        fileSystemStorageService.generateTargetLocation("smartspec", smartSpecDataset.getCodePath());
        Path filePath = fileSystemStorageService.getFileNameLocation(Param.SMARTSPEC_SPACES_FILE);
        // Load json data and save in database
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {

            List<Map<String, Object>> spaces = objectMapper.readValue(filePath.toFile(), new TypeReference<>(){});
            List<Long> ids = new ArrayList<>();
            DatasetFactory datasetFactory = new DatasetFactory();

            for (Map<String, Object> space : spaces) {
                SmartSpecSpace entityToSave = new SmartSpecSpace();
                entityToSave.setJsonId(Long.parseLong(String.valueOf(space.get("id"))));
                entityToSave.setDescription((String) space.get("description"));
                entityToSave.setSmartSpecDataset(smartSpecDataset);
                repository.save(entityToSave);
                ids.add(entityToSave.getJsonId());
            }

            String datasetFilePath = fileSystemStorageService.getFileNameLocation(Param.SMARTSPEC_TRAJETORY_FILE).toFile().getAbsolutePath();
            String baseStartDate = datasetFactory.generateSpaceDataset(datasetFilePath,
                    fileSystemStorageService.getGeneratedFilesLocation().toFile().getAbsolutePath(),
                    ids);
            smartSpecDataset.setBaseStartDate(baseStartDate);
            smartSpecDatasetService.update(smartSpecDataset);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
