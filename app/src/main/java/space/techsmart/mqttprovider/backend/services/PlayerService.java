package space.techsmart.mqttprovider.backend.services;

import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import space.techsmart.mqttprovider.backend.engine.MainProvider;
import space.techsmart.mqttprovider.backend.entities.Scenario;
import space.techsmart.mqttprovider.backend.utils.Param;

@Data
@Service
public class PlayerService {

    private MainProvider provider;

    @Async
    public ListenableFuture<String> playerTask(String brokerIP, String brokerPort, long timeOfExperiment, Scenario scenario, FileSystemStorageService fileSystemStorageService) {
        // Simulate a long running task
        System.out.println("BROKER IP:" + brokerIP);
        System.out.println("BROKER PORT:" + brokerPort);
        System.out.println("SCENARIO NAME:" + scenario.getName());
        System.out.println("NUMBER DEVICES:" + scenario.getDevices().size());

        Param.address = "tcp://"+brokerIP+":"+brokerPort;
        Param.time_of_experiment = timeOfExperiment;

        provider = new MainProvider();
        provider.playMQTTProvider(scenario.getDevices(), fileSystemStorageService);

        return AsyncResult.forValue("Some result");
    }

    public void abortDevices() {
        provider.abortDevices();
    }

}
