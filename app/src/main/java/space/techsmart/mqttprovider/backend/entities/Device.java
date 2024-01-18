package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Device extends AbstractEntity{

    @NotEmpty
    private String deviceId;

    @NotEmpty
    private String description;

    @NotNull
    private Integer instances = 1;

    @ManyToOne
    @NotNull
    private DeviceGroup deviceGroup;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Set<Actuator> actuators = new HashSet<>();

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Set<TDSensor> tdSensors = new HashSet<>();

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Set<EDSensor> edSensors = new HashSet<>();

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Set<MobileSensor> mobileSensors = new HashSet<>();


}
