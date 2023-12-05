package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDataset;
import space.techsmart.mqttprovider.backend.services.SmartSpecDatasetService;

@Route(value="smartspecdataset", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("SmartSPEC Datasets | MQTTProvider")
public class SmartSpecDatasetView extends VerticalLayout {

    private SmartSpecDatasetService smartSpecDatasetService;

    public SmartSpecDatasetView(SmartSpecDatasetService smartSpecDatasetService) {

        this.smartSpecDatasetService = smartSpecDatasetService;

        // crud instance
        GridCrud<SmartSpecDataset> crud = new GridCrud<>(SmartSpecDataset.class, smartSpecDatasetService);

        // grid configuration
        crud.getGrid().setColumns("codePath","name","description","baseStartDate");
        crud.getGrid().setColumnReorderingAllowed(true);
        //crud.getGrid().addColumn(createStatusComponentRenderer()).setHeader("Data Generated?").setWidth("150px").setFlexGrow(0);

        crud.getGrid().addComponentColumn(smartSpecDataset -> {
            Button editButton = new Button(
                    "Add Files");
            editButton.addClickListener(e ->
                    editButton.getUI().ifPresent(ui -> ui.navigate(
                            SmartSpecDatasetFileView.class,
                            new RouteParameters("datasetID", smartSpecDataset.getId().toString())))
            );
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        crud.getGrid().addComponentColumn(smartSpecDataset -> {
            Button genButton = new Button(
                    "Generate Data");
            genButton.addClickListener(e ->
                    genButton.getUI().ifPresent(ui -> ui.navigate(
                            SmartSpecDatasetDataView.class,
                            new RouteParameters("datasetID", smartSpecDataset.getId().toString())))
            );
            return genButton;
        }).setWidth("200px").setFlexGrow(0);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "codePath","name","description");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name","description");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "name","description");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "name");

        add(
                new H2("SmartSpec Datasets"),
                crud
        );
    }

    private static final SerializableBiConsumer<Span, SmartSpecDataset> statusComponentUpdater = (
            span, smartSpecDataset) -> {
        boolean isAvailable = "Available".equals(smartSpecDataset.getStatus());
        String theme = String.format("badge %s",
                isAvailable ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(smartSpecDataset.getStatus());
    };

    private static ComponentRenderer<Span, SmartSpecDataset> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

}
