package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;
import space.techsmart.mqttprovider.backend.entities.Actuator;
import space.techsmart.mqttprovider.backend.entities.Device;
import space.techsmart.mqttprovider.backend.entities.Scenario;
import space.techsmart.mqttprovider.backend.services.DeviceService;
import space.techsmart.mqttprovider.backend.services.ScenarioService;

@Route(value="", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Scenarios | MQTTProvider")
public class ScenarioView extends VerticalLayout {

    public ScenarioView(ScenarioService scenarioService, DeviceService deviceService) {

        // crud instance
        GridCrud<Scenario> crud = new GridCrud<>(Scenario.class, scenarioService);

        // grid configuration
        crud.getGrid().setColumns("name","description");
        crud.getGrid().addColumn(scenario -> scenario.getDevices().toString()).setHeader("Devices");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setVisibleProperties("name","description","devices");
        crud.getCrudFormFactory().setFieldProvider("devices",
                new CheckBoxGroupProvider<>(deviceService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("devices",
                new CheckBoxGroupProvider<>("Devices", deviceService.findAll(), Device::getDeviceId));

        add(
                new H2("Scenarios"),
                crud
        );
    }
}
