package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import space.techsmart.mqttprovider.backend.utils.DataType;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TDSensor extends AbstractEntity{

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String objectId;
    @NotNull
    private DataType dataType;
    @NotNull
    private Integer periodicity;
    @NotNull
    private String min;
    @NotNull
    private String max;

    @Transient
    private String deviceId;
    @Transient
    private String apiKey;
    @Transient
    private int numberOfDevices;

}
