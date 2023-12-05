package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.DeviceGroup;
import space.techsmart.mqttprovider.backend.repositories.DeviceGroupRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceGroupService implements CrudListener<DeviceGroup> {

    private final DeviceGroupRepository repository;

    @Override
    public Collection<DeviceGroup> findAll() {
        return repository.findAll();
    }

    @Override
    public DeviceGroup add(DeviceGroup deviceGroup) {
        return repository.save(deviceGroup);
    }

    @Override
    public DeviceGroup update(DeviceGroup deviceGroup) {
        return repository.save(deviceGroup);
    }

    @Override
    public void delete(DeviceGroup deviceGroup) {
        repository.delete(deviceGroup);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<DeviceGroup> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
