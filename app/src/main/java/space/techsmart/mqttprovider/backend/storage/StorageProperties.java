package space.techsmart.mqttprovider.backend.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@Data
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
    private String sumoLocation = location + File.separator + "sumo";
    private String smartSpecLocation = location + File.separator + "smartspec";

}
