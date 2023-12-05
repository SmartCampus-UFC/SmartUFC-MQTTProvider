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
public class SumoTrace extends AbstractEntity{

    @NotEmpty
    private String fileName;
    @NotNull
    private Long contentLength;
    @NotEmpty
    private String mimeType;
    @NotEmpty
    private String codePath;

    private String description;
    /*@NotEmpty
    private String absolutePath;
    @NotEmpty
    private String relativePath;*/

}
