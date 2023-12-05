package space.techsmart.mqttprovider.backend.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EDCommandEntry {

    private long startTimestamp;
    private long endTimestamp;

}
