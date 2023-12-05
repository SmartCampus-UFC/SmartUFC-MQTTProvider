package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import space.techsmart.mqttprovider.backend.entities.*;
import space.techsmart.mqttprovider.backend.services.*;
import space.techsmart.mqttprovider.backend.entities.EDSensor;

@Route(value="devices", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Devices | MQTTProvider")
public class DeviceView extends VerticalLayout {

    public DeviceView(DeviceService deviceService,
                      DeviceGroupService deviceGroupService,
                      ActuatorService actuatorService,
                      TDSensorService tdSensorService,
                      EDSensorService edSensorService,
                      MobileSensorService mobileSensorService) {

        // crud instance
        GridCrud<Device> crud = new GridCrud<>(Device.class, deviceService);

        // grid configuration
        crud.getGrid().setColumns("deviceId","description");
        crud.getGrid().addColumn(device -> device.getDeviceGroup().getName()).setHeader("Device Group");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("deviceId","description","deviceGroup","actuators","tdSensors","edSensors","mobileSensors");

        crud.getCrudFormFactory().setFieldProvider("deviceGroup",
                new ComboBoxProvider<>(deviceGroupService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("deviceGroup",
                new ComboBoxProvider<>("Device Group", deviceGroupService.findAll(), new TextRenderer<>(DeviceGroup::getName), DeviceGroup::getName));

        crud.getCrudFormFactory().setFieldProvider("actuators",
                new CheckBoxGroupProvider<>(actuatorService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("actuators",
                new CheckBoxGroupProvider<>("Actuators", actuatorService.findAll(), Actuator::getName));

        crud.getCrudFormFactory().setFieldProvider("tdSensors",
                new CheckBoxGroupProvider<>(tdSensorService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("tdSensors",
                new CheckBoxGroupProvider<>("TD Sensors", tdSensorService.findAll(), TDSensor::getName));

        crud.getCrudFormFactory().setFieldProvider("edSensors",
                new CheckBoxGroupProvider<>(edSensorService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("edSensors",
                new CheckBoxGroupProvider<>("ED Sensors", edSensorService.findAll(), EDSensor::getName));

        crud.getCrudFormFactory().setFieldProvider("mobileSensors",
                new CheckBoxGroupProvider<>(mobileSensorService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("mobileSensors",
                new CheckBoxGroupProvider<>("Mobile Sensors", mobileSensorService.findAll(), MobileSensor::getName));

        add(
                new H2("Devices"),
                crud
        );

        //var crud = new GridCrud<>(Device.class, service);

        //crud.getGrid().addColumn("commands").setEditorComponent(comboBox);
        //crud.getGrid().addItemClickListener(event -> UI.getCurrent().navigate("/devices?scenario=" + event.getItem().getName()));
        /*crud.getCrudFormFactory().setFieldProvider("commands", () -> {
            CheckboxGroup<Command> checkboxes = new CheckboxGroup<>("Commands", commandService.findAll());
            checkboxes.setItemLabelGenerator(Command::getName);
            return checkboxes;
            /*MultiSelectComboBox<Command> comboBox = new MultiSelectComboBox<>("Commands");
            comboBox.setItems(commandService.findAll());
            comboBox.setItemLabelGenerator(Command::getName);
            return comboBox;*/
        //});

    }
}
