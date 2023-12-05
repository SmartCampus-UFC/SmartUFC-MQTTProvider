package space.techsmart.mqttprovider.backend.dataset.creators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import space.techsmart.mqttprovider.backend.utils.DateConversion;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DoorSensorEntry {

    @EqualsAndHashCode.Include
    private Long id;
    private Long date;

    public static void gererateDatasetFiles (String sourceFilePath, String destinationPath) {
        List<DoorSensorEntry> doorSensorEntries = DoorSensorEntry.mapCSV(sourceFilePath);
        // Get all ids
        List<Long> doorIds = doorSensorEntries.parallelStream()
                        .sorted(comparing(DoorSensorEntry::getId).reversed())
                        .map(DoorSensorEntry::getId).distinct()
                        .collect(toList());
        for (Long id : doorIds) {
            String datasetEntries = doorSensorEntries.parallelStream()
                    .filter(t -> t.getId() == id)
                    .sorted(comparing(DoorSensorEntry::getDate))
                    .map(e -> e.getDate() + ",open")
                    .collect(Collectors.joining("\n"));
            File csvFile = new File(destinationPath+File.separator+Param.SENSOR_PREFIXFILE+id+".csv");
            try {
                FileWriter fileWriter = new FileWriter(csvFile);
                fileWriter.write(datasetEntries);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static List<DoorSensorEntry> mapCSV(String csvFilePath) {
        List<DoorSensorEntry> doorList;
        File inputF = new File(csvFilePath);
        InputStream inputFS = null;
        try {
            inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            doorList = br.lines().skip(1).map(csv2Obj).collect(toList());
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return doorList;
    }

    private static Function<String, DoorSensorEntry> csv2Obj = (line) -> {
        String[] record = line.split(",");// This can be delimiter which
        DoorSensorEntry entry = new DoorSensorEntry();
        if (record[0] != null && record[0].trim().length() > 0) {
            entry.setId(Long.valueOf(record[0]));
        }
        if (record[1] != null && record[1].trim().length() > 0) {
            entry.setDate(DateConversion.getMillisFromString(record[1]));
        }
        return entry;
    };

}
