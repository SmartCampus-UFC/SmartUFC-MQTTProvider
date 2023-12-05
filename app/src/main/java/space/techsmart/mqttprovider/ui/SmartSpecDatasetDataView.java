package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import space.techsmart.mqttprovider.backend.entities.*;
import space.techsmart.mqttprovider.backend.services.*;
import space.techsmart.mqttprovider.backend.services.FileSystemStorageService;

@Route(value="smartspecdata/:datasetID", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Sumo Traces | MQTTProvider")
public class SmartSpecDatasetDataView extends VerticalLayout implements BeforeEnterObserver {

    //private SmartSpecDatasetFileService smartSpecDatasetFileService;
    private FileSystemStorageService fileSystemStorageService;
    private SmartSpecDatasetService smartSpecDatasetService;
    //private GridCrud<SmartSpecDatasetFile> crud;

    private SmartSpecMetaSensorService smartSpecMetaSensorService;
    private SmartSpecSensorService smartSpecSensorService;
    private SmartSpecSpaceService smartSpecSpaceService;

    private Long datasetID;
    private ProgressBar progressBar = new ProgressBar();

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        final RouteParameters urlParameters = beforeEnterEvent.getRouteParameters();
        urlParameters.getLong("datasetID").ifPresent(value -> this.datasetID = value);
    }

    public SmartSpecDatasetDataView(SmartSpecMetaSensorService smartSpecMetaSensorService,
                                    SmartSpecSensorService smartSpecSensorService,
                                    SmartSpecSpaceService smartSpecSpaceService,
                                    SmartSpecDatasetService smartSpecDatasetService,
                                    FileSystemStorageService fileSystemStorageService) {

        //this.smartSpecDatasetFileService = smartSpecDatasetFileService;
        this.fileSystemStorageService = fileSystemStorageService;
        this.smartSpecDatasetService = smartSpecDatasetService;

        this.smartSpecMetaSensorService = smartSpecMetaSensorService;
        this.smartSpecSensorService = smartSpecSensorService;
        this.smartSpecSpaceService = smartSpecSpaceService;

        // ### Crud Meta Sensor ###
        GridCrud<SmartSpecMetaSensor> crudms = new GridCrud<>(SmartSpecMetaSensor.class, smartSpecMetaSensorService);
        // grid configuration
        crudms.getGrid().setColumns("jsonId","description");
        crudms.getGrid().setColumnReorderingAllowed(true);
        crudms.getGrid().setItems(smartSpecMetaSensorService.findAllBySmartSpecDatasetId(datasetID));
        // form configuration
        crudms.getCrudFormFactory().setUseBeanValidation(true);
        crudms.setAddOperationVisible(false);
        crudms.setUpdateOperationVisible(false);
        crudms.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "jsonId");

        // ### Crud Sensor ###
        GridCrud<SmartSpecSensor> crudsn = new GridCrud<>(SmartSpecSensor.class, smartSpecSensorService);
        // grid configuration
        crudsn.getGrid().setColumns("jsonId","description","jsonMetaSensorId");
        //crudsn.getGrid().addColumn(sensor -> sensor.getSmartSpecMetaSensor().getId()).setHeader("Meta Sensor Id");
        //crudsn.getGrid().addColumn(sensor -> sensor.getSmartSpecDatasetFile().getFileName()).setHeader("Dataset File Name");
        crudsn.getGrid().setColumnReorderingAllowed(true);
        crudsn.getGrid().setItems(smartSpecSensorService.findAllBySmartSpecDatasetId(datasetID));
        // form configuration
        crudsn.getCrudFormFactory().setUseBeanValidation(true);
        crudsn.setAddOperationVisible(false);
        crudsn.setUpdateOperationVisible(false);
        crudsn.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "jsonId");

        // ### Crud Sensor ###
        GridCrud<SmartSpecSpace> crudsp = new GridCrud<>(SmartSpecSpace.class, smartSpecSpaceService);
        // grid configuration
        crudsp.getGrid().setColumns("jsonId","description");
        //crudsp.getGrid().addColumn(sensor -> sensor.getSmartSpecDatasetFile().getFileName()).setHeader("Dataset File Name");
        crudsp.getGrid().setColumnReorderingAllowed(true);
        crudsp.getGrid().setItems(smartSpecSpaceService.findAllBySmartSpecDatasetId(datasetID));
        // form configuration
        crudsp.getCrudFormFactory().setUseBeanValidation(true);
        crudsp.setAddOperationVisible(false);
        crudsp.setUpdateOperationVisible(false);
        crudsp.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "jsonId");

        progressBar.setWidth("15em");
        progressBar.setIndeterminate(true);

        progressBar.setVisible(false);

        Button startButton = new Button("Generate Data", clickEvent -> {
            Notification.show("Data Generation Started!");
            progressBar.setVisible(true);
            generateData();
            Notification.show("New Data Generated");
            progressBar.setVisible(false);
            crudms.refreshGrid();
            crudsn.refreshGrid();
            crudsp.refreshGrid();
        });

        add(
                new H2("Dataset Imported Data"),
                new H3("Model Sensors"),
                crudms,
                new H3("Sensors"),
                crudsn,
                new H3("Spaces"),
                crudsp,
                startButton
        );

    }

    private void generateData() {
        SmartSpecDataset smartSpecDataset = this.smartSpecDatasetService.findById(datasetID);
        this.fileSystemStorageService.generateTargetLocation("smartspec", smartSpecDataset.getCodePath());
        this.fileSystemStorageService.createGeneratedFilesLocation();
        //this.smartSpecDatasetService.generateProcessedDatasets(smartSpecDataset,fileSystemStorageService);
        this.smartSpecMetaSensorService.generateNewData(smartSpecDataset,fileSystemStorageService);
        this.smartSpecSensorService.generateNewData(smartSpecDataset,smartSpecMetaSensorService,fileSystemStorageService);
        this.smartSpecSpaceService.generateNewData(smartSpecDataset, smartSpecDatasetService, fileSystemStorageService);
    }

}
