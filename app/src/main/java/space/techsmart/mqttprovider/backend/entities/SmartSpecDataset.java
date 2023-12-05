package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SmartSpecDataset extends AbstractEntity{

    private String name;

    @NotEmpty
    private String codePath;

    private String description;

    private String status;

    private String baseStartDate;

}
