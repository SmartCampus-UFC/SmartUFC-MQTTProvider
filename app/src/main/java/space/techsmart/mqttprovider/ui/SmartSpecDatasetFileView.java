package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDatasetFile;
import space.techsmart.mqttprovider.backend.entities.SmartSpecDataset;
import space.techsmart.mqttprovider.backend.services.FileSystemStorageService;
import space.techsmart.mqttprovider.backend.services.SmartSpecDatasetFileService;
import space.techsmart.mqttprovider.backend.services.SmartSpecDatasetService;

import java.io.IOException;
import java.io.InputStream;

@Route(value="smartspecfiles/:datasetID", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Sumo Traces | MQTTProvider")
public class SmartSpecDatasetFileView extends VerticalLayout implements BeforeEnterObserver {

    private SmartSpecDatasetFileService smartSpecDatasetFileService;
    private FileSystemStorageService fileSystemStorageService;
    private SmartSpecDatasetService smartSpecDatasetService;

    private GridCrud<SmartSpecDatasetFile> crud;
    private Long datasetID;

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        final RouteParameters urlParameters = beforeEnterEvent.getRouteParameters();
        urlParameters.getLong("datasetID").ifPresent(value -> this.datasetID = value);
    }

    public SmartSpecDatasetFileView(SmartSpecDatasetFileService smartSpecDatasetFileService, SmartSpecDatasetService smartSpecDatasetService, FileSystemStorageService fileSystemStorageService) {

        this.smartSpecDatasetFileService = smartSpecDatasetFileService;
        this.fileSystemStorageService = fileSystemStorageService;
        this.smartSpecDatasetService = smartSpecDatasetService;

        // crud instance
        crud = new GridCrud<>(SmartSpecDatasetFile.class, smartSpecDatasetFileService);

        // grid configuration
        crud.getGrid().setColumns("fileName","description","contentLength","mimeType");
        crud.getGrid().setColumnReorderingAllowed(true);

        crud.getGrid().setItems(smartSpecDatasetFileService.findAllBySmartSpecDatasetId(datasetID));

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.setAddOperationVisible(false);

        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "fileName","description","contentLength","mimeType");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "description");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "fileName");
        crud.setDeleteOperation(datasetFile -> deleteFile(datasetFile));

        MultiFileMemoryBuffer multiFileMemoryBuffer = new MultiFileMemoryBuffer();
        Upload multiFileUpload = new Upload(multiFileMemoryBuffer);

        multiFileUpload.addSucceededListener(event -> {
            // Get information about the uploaded file
            String fileName = event.getFileName();
            InputStream fileData = multiFileMemoryBuffer.getInputStream(fileName);
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
            try {
                processFile(fileData, fileName, contentLength, mimeType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        add(
                new H2("Dataset Files"),
                crud,
                multiFileUpload
        );

    }

    private void deleteFile(SmartSpecDatasetFile smartSpecDatasetFile) {
        fileSystemStorageService.generateTargetLocation("smartspec", smartSpecDatasetFile.getSmartSpecDataset().getCodePath());
        if (fileSystemStorageService.delete(fileSystemStorageService.getFileNameLocation(smartSpecDatasetFile.getFileName()))) {
            smartSpecDatasetFileService.delete(smartSpecDatasetFile);
            Notification.show("File removed!");
            refreshGrid();
        }
        else Notification.show("File not found! Operation failed!");
    }

    private void refreshGrid() {
        crud.getGrid().setItems(smartSpecDatasetFileService.findAllBySmartSpecDatasetId(datasetID));
    }

    private void processFile(InputStream inputStream, String fileName, long contentLength, String mimeType) throws IOException {
        SmartSpecDatasetFile smartSpecDatasetFile = new SmartSpecDatasetFile();
        smartSpecDatasetFile.setFileName(fileName);
        smartSpecDatasetFile.setContentLength(contentLength);
        smartSpecDatasetFile.setMimeType(mimeType);
        smartSpecDatasetFile.setDescription("");
        SmartSpecDataset smartSpecDataset = smartSpecDatasetService.findById(datasetID);
        smartSpecDatasetFile.setSmartSpecDataset(smartSpecDataset);
        smartSpecDatasetFileService.add(smartSpecDatasetFile);
        fileSystemStorageService.generateTargetLocation("smartspec",smartSpecDataset.getCodePath());
        fileSystemStorageService.store(inputStream,fileName,contentLength);
        Notification.show("File uploaded!");
        refreshGrid();
    }
}
