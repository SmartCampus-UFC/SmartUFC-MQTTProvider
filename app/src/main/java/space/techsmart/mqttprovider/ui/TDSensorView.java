package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import space.techsmart.mqttprovider.backend.entities.TDSensor;
import space.techsmart.mqttprovider.backend.services.TDSensorService;

@Route(value="tdsensors", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Time-driven Sensors | MQTTProvider")
public class TDSensorView extends VerticalLayout {

    public TDSensorView(TDSensorService service) {
        var crud = new GridCrud<>(TDSensor.class, service);
        crud.getGrid().setColumns("name","description","objectId","dataType","periodicity","min","max");
        //crud.getGrid().addItemClickListener(event -> UI.getCurrent().navigate("/devices?scenario=" + event.getItem().getName()));
        crud.getCrudFormFactory().setVisibleProperties("name","description","objectId","dataType","periodicity","min","max");
        add(
                new H2("Time-driven Sensors"),
                crud
        );
    }
}
