package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import space.techsmart.mqttprovider.backend.entities.DeviceGroup;
import space.techsmart.mqttprovider.backend.services.DeviceGroupService;

@Route(value="devicegroups", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Device Groups | MQTTProvider")
public class DeviceGroupView extends VerticalLayout {

    public DeviceGroupView(DeviceGroupService service) {
        var crud = new GridCrud<>(DeviceGroup.class, service);
        crud.getGrid().setColumns("name","description","apiKey");
        //crud.getGrid().addItemClickListener(event -> UI.getCurrent().navigate("/devices?scenario=" + event.getItem().getName()));
        crud.getCrudFormFactory().setVisibleProperties("name","description","apiKey");
        add(
                new H2("Device Groups"),
                crud
        );
    }
}
