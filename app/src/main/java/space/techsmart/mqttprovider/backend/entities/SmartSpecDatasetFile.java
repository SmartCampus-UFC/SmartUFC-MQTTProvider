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
public class SmartSpecDatasetFile extends AbstractEntity{

    @NotEmpty
    private String fileName;
    @NotNull
    private Long contentLength;
    @NotEmpty
    private String mimeType;

    private String description;

    @ManyToOne
    @NotNull
    private SmartSpecDataset smartSpecDataset;


}
