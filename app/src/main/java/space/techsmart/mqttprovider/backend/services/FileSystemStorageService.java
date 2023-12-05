package space.techsmart.mqttprovider.backend.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import space.techsmart.mqttprovider.backend.storage.StorageException;
import space.techsmart.mqttprovider.backend.storage.StorageFileNotFoundException;
import space.techsmart.mqttprovider.backend.storage.StorageProperties;
import space.techsmart.mqttprovider.backend.storage.StorageService;
import space.techsmart.mqttprovider.backend.utils.Param;
import space.techsmart.mqttprovider.backend.utils.RandomDirName;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Data
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final Path sumoLocation;
    private final Path smartSpecLocation;
    private Path targetLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {

        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
        this.sumoLocation = Paths.get(properties.getSumoLocation());
        this.smartSpecLocation = Paths.get(properties.getSmartSpecLocation());

    }

    public Path generateTargetLocation(String type) {
        if (type == "sumo") {
            this.targetLocation = RandomDirName.generateRandomDirPath(this.sumoLocation.toFile().getAbsolutePath());
        } else this.targetLocation = RandomDirName.generateRandomDirPath(this.smartSpecLocation.toFile().getAbsolutePath());
        return this.targetLocation;
    }

    public Path generateTargetLocation(String type, String codePath) {
        if (type == "sumo") {
            this.targetLocation = Paths.get(this.sumoLocation.toFile().getAbsolutePath() + File.separator + codePath);
        } else this.targetLocation = Paths.get(this.smartSpecLocation.toFile().getAbsolutePath() + File.separator + codePath);
        return this.targetLocation;
    }

    public Path getCodePathLocation(String codePath) {
        String rootPathName = this.targetLocation.getParent().toFile().getAbsolutePath();
        Path path = Paths.get(rootPathName + File.separator + codePath);
        return path;
    }

    public Path createGeneratedFilesLocation() {
        String rootPathName = this.targetLocation.toFile().getAbsolutePath();
        Path path = Paths.get(rootPathName + File.separator + Param.GENERATEDFILES_DIR_NAME);
        delete(path);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    public Path getGeneratedFilesLocation() {
        String rootPathName = this.targetLocation.toFile().getAbsolutePath();
        Path path = Paths.get(rootPathName + File.separator + Param.GENERATEDFILES_DIR_NAME);
        return path;
    }

    public Path getFileNameLocation(String fileName) {
        String rootPathName = this.targetLocation.toFile().getAbsolutePath();
        Path path = Paths.get(rootPathName + File.separator + fileName);
        return path;
    }

    @Override
    public void store(InputStream inputStream, String fileName, long contentLength) {
        try {
            if (contentLength <= 0) {
                throw new StorageException("Failed to store empty file.");
            }
            if (!Files.exists(this.targetLocation)) Files.createDirectories(this.targetLocation);
            Path destinationFile = this.targetLocation.resolve(
                            Paths.get(fileName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.targetLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public boolean delete(Path path) {
        try {
            return FileSystemUtils.deleteRecursively(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(sumoLocation);
            Files.createDirectories(smartSpecLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
