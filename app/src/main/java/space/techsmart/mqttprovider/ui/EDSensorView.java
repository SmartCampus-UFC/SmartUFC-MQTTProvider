package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import space.techsmart.mqttprovider.backend.entities.DeviceGroup;
import space.techsmart.mqttprovider.backend.entities.EDSensor;
import space.techsmart.mqttprovider.backend.entities.Scenario;
import space.techsmart.mqttprovider.backend.entities.SmartSpecSensor;
import space.techsmart.mqttprovider.backend.services.EDSensorService;
import space.techsmart.mqttprovider.backend.services.SmartSpecSensorService;

@Route(value="edsensors", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Event-driven Sensors | MQTTProvider")
public class EDSensorView extends VerticalLayout {

    public EDSensorView(EDSensorService service, SmartSpecSensorService smartSpecSensorService) {
        var crud = new GridCrud<>(EDSensor.class, service);
        crud.getGrid().setColumns("name","description","objectId");
        crud.getGrid().addColumn(edsensor -> edsensor.getSmartSpecSensor().getJsonId()).setHeader("SmartSpec Sensor Id");
        crud.getGrid().setColumnReorderingAllowed(true);

        /*ComboBox<SmartSpecSensor> sensorBox = new ComboBox<>("SmartSpec Sensors");
        sensorBox.setItems(smartSpecSensorService.findAll());
        sensorBox.setItemLabelGenerator(SmartSpecSensor::getDescription);*/

        crud.getCrudFormFactory().setFieldProvider("smartSpecSensor",
                new ComboBoxProvider<>(smartSpecSensorService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("smartSpecSensor",
                new ComboBoxProvider<>("SmartSpec Sensors", smartSpecSensorService.findAll(), new TextRenderer<>(SmartSpecSensor::getDescription), SmartSpecSensor::getDescription));

        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name","description","objectId","smartSpecSensor");

        //crud.getGrid().addItemClickListener(event -> UI.getCurrent().navigate("/devices?scenario=" + event.getItem().getName()));
        //crud.getCrudFormFactory().setVisibleProperties("name","description","objectId","dataType","mode","lambda","duration","min","max");
        add(
                new H2("Event-driven Sensors"),
                crud
        );
    }
}
