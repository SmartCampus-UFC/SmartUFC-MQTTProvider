package space.techsmart.mqttprovider.backend.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public final class RandomDirName {

    public static String generateRandomDirCode(){
        return UUID.randomUUID().toString();
    }
    public static Path generateRandomDirPath(String baseDir){
        return Paths.get(baseDir + File.separator + generateRandomDirCode());
    }

}
