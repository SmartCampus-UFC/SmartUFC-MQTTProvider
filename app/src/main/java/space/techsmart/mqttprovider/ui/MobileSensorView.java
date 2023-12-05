package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import space.techsmart.mqttprovider.backend.entities.Device;
import space.techsmart.mqttprovider.backend.entities.DeviceGroup;
import space.techsmart.mqttprovider.backend.entities.MobileSensor;
import space.techsmart.mqttprovider.backend.entities.SumoTrace;
import space.techsmart.mqttprovider.backend.services.MobileSensorService;
import space.techsmart.mqttprovider.backend.services.SumoTraceService;

@Route(value="mobilesensors", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Mobile Sensors | MQTTProvider")
public class MobileSensorView extends VerticalLayout {

    public MobileSensorView(MobileSensorService mobileSensorService, SumoTraceService sumoTraceService) {

        // crud instance
        GridCrud<MobileSensor> crud = new GridCrud<>(MobileSensor.class, mobileSensorService);

        // grid configuration
        crud.getGrid().setColumns("name","description","objectId","mobileId","periodicity");
        crud.getGrid().addColumn(sensor -> sensor.getSumoTrace().getFileName()).setHeader("Sumo Trace");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name","description","objectId","mobileId","periodicity","sumoTrace");

        crud.getCrudFormFactory().setFieldProvider("sumoTrace",
                new ComboBoxProvider<>(sumoTraceService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("sumoTrace",
                new ComboBoxProvider<>("Device Group", sumoTraceService.findAll(), new TextRenderer<>(SumoTrace::getFileName), SumoTrace::getFileName));

       add(
                new H2("Mobile Sensors"),
                crud
        );
    }
}
