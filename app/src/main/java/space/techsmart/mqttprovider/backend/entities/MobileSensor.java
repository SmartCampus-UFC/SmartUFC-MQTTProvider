package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MobileSensor extends AbstractEntity{

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String objectId;
    @NotEmpty
    private String mobileId;
    @NotNull
    private Integer periodicity;
    @ManyToOne
    @NotNull
    private SumoTrace sumoTrace;

    @Transient
    private String deviceId;
    @Transient
    private String apiKey;
    @Transient
    private int numberOfDevices;

}
