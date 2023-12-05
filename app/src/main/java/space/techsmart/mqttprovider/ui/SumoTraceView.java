package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import space.techsmart.mqttprovider.backend.entities.SumoTrace;
import space.techsmart.mqttprovider.backend.services.FileSystemStorageService;
import space.techsmart.mqttprovider.backend.services.SumoTraceService;

import java.io.*;

@Route(value="sumotraces", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Sumo Traces | MQTTProvider")
public class SumoTraceView extends VerticalLayout {

    private SumoTraceService sumoTraceService;
    private FileSystemStorageService fileSystemStorageService;
    private GridCrud<SumoTrace> crud;
    public SumoTraceView(SumoTraceService sumoTraceService, FileSystemStorageService fileSystemStorageService) {

        this.sumoTraceService = sumoTraceService;
        this.fileSystemStorageService = fileSystemStorageService;

        // crud instance
        crud = new GridCrud<>(SumoTrace.class, sumoTraceService);

        // grid configuration
        crud.getGrid().setColumns("codePath","fileName","description");
        crud.getGrid().setColumnReorderingAllowed(true);

        //crud.getGrid().setColumns("fileName","description","contentLength","mimeType","absolutePath");

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.setAddOperationVisible(false);
        //crud.setUpdateOperationVisible(false);

        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "codePath","fileName","description","contentLength","mimeType");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "description");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "fileName");
        crud.setDeleteOperation(sumoTrace -> deleteFile(sumoTrace));

        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload singleFileUpload = new Upload(memoryBuffer);

        int maxFileSizeInBytes = 10 * 1024 * 1024; // 10MB
        singleFileUpload.setMaxFileSize(maxFileSizeInBytes);

        fileSystemStorageService.generateTargetLocation("sumo");

        singleFileUpload.addSucceededListener(event -> {
            // Get information about the uploaded file
            InputStream fileData = memoryBuffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
            try {
                processFile(fileData, fileName, contentLength, mimeType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        add(
                new H2("Sumo Traces"),
                crud,
                singleFileUpload
        );

    }

    private void deleteFile(SumoTrace sumoTrace) {
        if (fileSystemStorageService.delete(fileSystemStorageService.getCodePathLocation(sumoTrace.getCodePath())))
            sumoTraceService.delete(sumoTrace);
        else Notification.show("File not found! Operation failed!");
        Notification.show("File removed!");
        crud.refreshGrid();
    }


    private void processFile(InputStream inputStream, String fileName, long contentLength, String mimeType) throws IOException {
        SumoTrace sumoTrace = new SumoTrace();
        sumoTrace.setFileName(fileName);
        sumoTrace.setContentLength(contentLength);
        sumoTrace.setMimeType(mimeType);
        sumoTrace.setDescription("");
        sumoTrace.setCodePath(fileSystemStorageService.getTargetLocation().getFileName().toString());
        sumoTraceService.add(sumoTrace);
        fileSystemStorageService.store(inputStream,fileName,contentLength);
        Notification.show("File uploaded!");
        crud.refreshGrid();
    }
}
