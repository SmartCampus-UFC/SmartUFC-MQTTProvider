package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeviceGroup extends AbstractEntity{

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String apiKey;

}
