package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SmartSpecSpace extends AbstractEntity{

    @NotNull
    private Long jsonId;


    @NotEmpty
    private String description;

    private String fileName;

    @ManyToOne
    @NotNull
    private SmartSpecDataset smartSpecDataset;

}
