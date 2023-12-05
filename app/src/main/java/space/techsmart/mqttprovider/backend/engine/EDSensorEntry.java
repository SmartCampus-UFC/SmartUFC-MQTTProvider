package space.techsmart.mqttprovider.backend.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EDSensorEntry {

    private long timestamp;
    private String value;

}
