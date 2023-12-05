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
public class WifiEntry {

    @EqualsAndHashCode.Include
    private Long id;
    private Long date;
    private Integer personId;

    public static void gererateDatasetFiles (String sourceFilePath, String destinationPath) {
        List<WifiEntry> wifiEntries = WifiEntry.mapCSV(sourceFilePath);
        // Get all ids
        List<Long> wifiIds = wifiEntries.parallelStream()
                .sorted(comparing(WifiEntry::getId).reversed())
                .map(WifiEntry::getId).distinct()
                .collect(toList());
        for (Long id : wifiIds) {
            String datasetEntries = wifiEntries.parallelStream()
                    .filter(t -> t.getId() == id)
                    .sorted(comparing(WifiEntry::getDate))
                    .map(e -> e.getDate() + "," + e.getPersonId())
                    .collect(Collectors.joining("\n"));
            File csvFile = new File(destinationPath+File.separator+ Param.SENSOR_PREFIXFILE+id+".csv");
            try {
                FileWriter fileWriter = new FileWriter(csvFile);
                fileWriter.write(datasetEntries);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public static List<WifiEntry> mapCSV(String csvFilePath) {
        List<WifiEntry> wifiList;
        File inputF = new File(csvFilePath);
        InputStream inputFS = null;
        try {
            inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            wifiList = br.lines().skip(1).map(csv2Obj).collect(Collectors.toList());
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wifiList;
    }

    private static Function<String, WifiEntry> csv2Obj = (line) -> {
        String[] record = line.split(",");// This can be delimiter which
        WifiEntry entry = new WifiEntry();
        if (record[0] != null && record[0].trim().length() > 0) {
            entry.setPersonId(Integer.valueOf(record[0]));
        }
        if (record[1] != null && record[1].trim().length() > 0) {
            entry.setDate(DateConversion.getMillisFromString(record[1]));
        }
        if (record[2] != null && record[2].trim().length() > 0) {
            entry.setId(Long.valueOf(record[2]));
        }
        return entry;
    };
    
}
