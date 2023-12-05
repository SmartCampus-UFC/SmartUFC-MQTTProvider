package space.techsmart.mqttprovider.backend.dataset.creators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import space.techsmart.mqttprovider.backend.utils.DateConversion;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SpaceEntry {

    @EqualsAndHashCode.Include
    private Long spaceID;
    private Long personID;
    private Long eventID;
    private Long startDateTime;
    private Long endDateTime;

    public static String gererateDatasetFiles (String sourceFilePath, String destinationPath, List<Long> ids) {
        List<SpaceEntry> spaceEntries = SpaceEntry.mapCSV(sourceFilePath);

        String baseStartDate = DateConversion.getDateStringFromMillis(spaceEntries.get(0).getStartDateTime());

        /* To simulate the commands for the actuators, we use data from the data.csv file.

        The general idea is that, for each event that occurs in a space, the first person to arrive activates the actuators (e.g., unlock a door, turn on the lights and air conditioning) and the last person to leave also activates them (e.g., lock a door). door, turn off the lights and air conditioning)

        In this sense, the MQTTProvider generates a CSV file for each space Id, from data.csv. This organization enables the association of actuators to a given space as well as the simulation of commands.

         */

        // Get all ids
        for (Long id : ids) {

            /*
            For each space id, select all entries grouping by event id and ordering by start date time

            The order by start date time aims to create a timeline of people ingressing in a specific event and space

            The result consists of a Map in which each key represents an event id and each value is a list of filtered and ordered entries.
            */
            Map<Long, List<SpaceEntry>> spaceMap = spaceEntries.parallelStream()
                    .filter(t -> t.getSpaceID() == id)
                    .sorted(comparing(SpaceEntry::getStartDateTime))
                    .collect(groupingBy(SpaceEntry::getEventID, toList()));
            /*
            Based on above result, create the entries for the specific space_x.csv, where x is a space id

            Each entry in the csv consists of an event that happens in that space, as well as the times the first person arrives and the last person leaves

            Furthermore, such events are ordered in time, from the first to the last that occurs in a given space.
            */
            List<SpaceEntry> spaceListToCsv = new ArrayList<>();
            spaceMap.forEach((key,spaceList)->{
                Long startDTMillis = null;
                Long endDTMillis = null;
                int count = 0;
                for (SpaceEntry space : spaceList) {
                    count++;
                    if (startDTMillis == null) {
                        startDTMillis = space.getStartDateTime();
                        endDTMillis = space.getEndDateTime();
                        continue;
                    }
                    Long start = space.getStartDateTime();
                    Long end = space.getEndDateTime();
                    if (start > endDTMillis) {
                        spaceListToCsv.add(new SpaceEntry(space.getSpaceID(),space.getPersonID(), space.getEventID(), startDTMillis,endDTMillis));
                        //datasetEntries.set(datasetEntries + "\n" + key + "," + startDTMillis + "," + endDTMillis);
                        startDTMillis = start;
                        endDTMillis = end;
                    } else if (end > endDTMillis) endDTMillis = end;
                    if (count == spaceList.size())
                        spaceListToCsv.add(new SpaceEntry(space.getSpaceID(),space.getPersonID(), space.getEventID(), startDTMillis,endDTMillis));
                }
            });
            //String datasetEntries = new String("event,start,end");
            String datasetEntries = spaceListToCsv.parallelStream()
                    .sorted(comparing(SpaceEntry::getStartDateTime))
                    .map(e -> e.getEventID() + "," + e.getStartDateTime() + "," + e.getEndDateTime())
                    .collect(Collectors.joining("\n"));
            File csvFile = new File(destinationPath+File.separator+ Param.SPACE_PREFIXFILE+id+".csv");
            try {
                FileWriter fileWriter = new FileWriter(csvFile);
                fileWriter.write(datasetEntries);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return baseStartDate;

    }
    
    public static List<SpaceEntry> mapCSV(String csvFilePath) {
        List<SpaceEntry> spaceList;
        File inputF = new File(csvFilePath);
        InputStream inputFS = null;
        try {
            inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            spaceList = br.lines().skip(1).map(csv2Obj).collect(Collectors.toList());
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return spaceList;
    }

    private static Function<String, SpaceEntry> csv2Obj = (line) -> {
        String[] record = line.split(",");// This can be delimiter which
        SpaceEntry entry = new SpaceEntry();
        if (record[0] != null && record[0].trim().length() > 0) {
            entry.setPersonID(Long.valueOf(record[0]));
        }
        if (record[1] != null && record[1].trim().length() > 0) {
            entry.setEventID(Long.valueOf(record[1]));
        }
        if (record[2] != null && record[2].trim().length() > 0) {
            entry.setSpaceID(Long.valueOf(record[2]));
        }
        if (record[3] != null && record[3].trim().length() > 0) {
            entry.setStartDateTime(DateConversion.getMillisFromString(record[3]));
        }
        if (record[4] != null && record[4].trim().length() > 0) {
            entry.setEndDateTime(DateConversion.getMillisFromString(record[4]));
        }
        return entry;
    };
    
}
