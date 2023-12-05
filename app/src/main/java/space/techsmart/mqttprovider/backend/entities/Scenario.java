package space.techsmart.mqttprovider.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class Scenario extends AbstractEntity{

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Set<Device> devices = new HashSet<>();

}
