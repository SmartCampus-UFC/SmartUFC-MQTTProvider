package space.techsmart.mqttprovider.backend.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Actuator extends AbstractEntity{

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String objectId;
    @NotNull
    private Integer periodicity;
    @NotEmpty
    private String defaultState;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Set<Command> commands = new HashSet<>();

    @ManyToOne
    private SmartSpecSpace smartSpecSpace;

    @Transient
    private String deviceId;
    @Transient
    private String apiKey;
    @Transient
    private int numberOfDevices;
}
