package space.techsmart.mqttprovider.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import space.techsmart.mqttprovider.backend.entities.Device;
import space.techsmart.mqttprovider.backend.repositories.DeviceRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService implements CrudListener<Device> {

    private final DeviceRepository repository;


    @Override
    public Collection<Device> findAll() {
        return repository.findAll();
    }

    @Override
    public Device add(Device device) {
        return repository.save(device);
    }

    @Override
    public Device update(Device device) {
        return repository.save(device);
    }

    @Override
    public void delete(Device device) {
        repository.delete(device);
    }

    public int countAll() {
        return (int) repository.count();
    }

    public List<Device> findByNameContainingIgnoreCase(String name) {
        return repository.findByDeviceIdContainingIgnoreCase(name);
    }

}
