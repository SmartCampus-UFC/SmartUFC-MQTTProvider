package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import space.techsmart.mqttprovider.backend.entities.Actuator;
import space.techsmart.mqttprovider.backend.entities.Command;
import space.techsmart.mqttprovider.backend.entities.SmartSpecSpace;
import space.techsmart.mqttprovider.backend.services.ActuatorService;
import space.techsmart.mqttprovider.backend.services.CommandService;
import space.techsmart.mqttprovider.backend.services.SmartSpecSpaceService;

@Route(value="actuators", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Actuators | MQTTProvider")
public class ActuatorView extends VerticalLayout {

    public ActuatorView(ActuatorService actuatorService, CommandService commandService, SmartSpecSpaceService smartSpecSpaceService) {

        // crud instance
        GridCrud<Actuator> crud = new GridCrud<>(Actuator.class, actuatorService);

        // grid configuration
        crud.getGrid().setColumns("name","description","objectId","periodicity","defaultState");
        crud.getGrid().addColumn(act -> {
            if (act.getSmartSpecSpace() != null) return act.getSmartSpecSpace().getJsonId();
            else return "None";
        }).setHeader("SmartSpec Space Id");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name","description","objectId","periodicity","defaultState","smartSpecSpace","commands");

        crud.getCrudFormFactory().setFieldProvider("smartSpecSpace",
                new ComboBoxProvider<>(smartSpecSpaceService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("smartSpecSpace",
                new ComboBoxProvider<>("SmartSpec Space", smartSpecSpaceService.findAll(), new TextRenderer<>(SmartSpecSpace::getDescription), SmartSpecSpace::getDescription));
        
        crud.getCrudFormFactory().setFieldProvider("commands",
                new CheckBoxGroupProvider<>(commandService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("commands",
                new CheckBoxGroupProvider<>("Commands", commandService.findAll(), Command::getName));

        add(
                new H2("Actuators"),
                crud
        );

        //var crud = new GridCrud<>(Actuator.class, service);

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
