package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.techsmart.mqttprovider.backend.entities.AbstractEntity;
import space.techsmart.mqttprovider.backend.utils.DataType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EDSensor extends AbstractEntity {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String objectId;
    @ManyToOne
    @NotNull
    private SmartSpecSensor smartSpecSensor;

    @Transient
    private String deviceId;
    @Transient
    private String apiKey;
    @Transient
    private int numberOfDevices;

}
