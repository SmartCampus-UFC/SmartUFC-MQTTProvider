package space.techsmart.mqttprovider.backend.dataset;

import lombok.Data;
import lombok.NoArgsConstructor;
import space.techsmart.mqttprovider.backend.dataset.creators.SpaceEntry;
import space.techsmart.mqttprovider.backend.dataset.creators.WifiEntry;
import space.techsmart.mqttprovider.backend.dataset.creators.DoorSensorEntry;
import space.techsmart.mqttprovider.backend.dataset.creators.TemperatureSensorEntry;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DatasetFactory {

    private final List<String> DEVICES = List.of("door","temperature","wifi");

    public void generateDeviceDataset(String sourceFilePath, String destinationPath, String description) {

        String key = getKey(description);
        if (key != null) {
            switch(key) {
                case "door":
                    DoorSensorEntry.gererateDatasetFiles(sourceFilePath, destinationPath);
                    break;
                case "temperature":
                    TemperatureSensorEntry.gererateDatasetFiles(sourceFilePath, destinationPath);
                    break;
                case "wifi":
                    WifiEntry.gererateDatasetFiles(sourceFilePath, destinationPath);
                    break;
                default:
                    // code block
            }
        }
    }

    public String generateSpaceDataset(String sourceFilePath, String destinationPath, List<Long> ids) {
        String baseStartDate = SpaceEntry.gererateDatasetFiles(sourceFilePath, destinationPath, ids);
        return baseStartDate;
    }

    public String getKey(String key) {
        List<String> matchKeys = DEVICES.stream().filter(k -> key.toLowerCase().contains(k)).collect(Collectors.toList());
        if (matchKeys.size() > 0) return matchKeys.get(0);
        else return null;
    }

}
