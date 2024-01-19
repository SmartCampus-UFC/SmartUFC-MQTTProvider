package space.techsmart.mqttprovider.backend.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.techsmart.mqttprovider.backend.entities.*;
import space.techsmart.mqttprovider.backend.services.FileSystemStorageService;
import space.techsmart.mqttprovider.backend.utils.DataType;
import space.techsmart.mqttprovider.backend.utils.Param;
import space.techsmart.mqttprovider.backend.utils.TopicGenerator;
import space.techsmart.mqttprovider.backend.entities.EDSensor;

import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainProvider {

	private ExecutorService executorService;
	private FileSystemStorageService fileSystemStorageService;
	private CountDownLatch latch;

	private void setTimeDrivenDevices(TDSensor temp) {
		String topic = TopicGenerator.getSensorPublishTopic(temp.getApiKey(), temp.getDeviceId());
		System.out.println(topic);
		setTimeDrivenSensor(temp.getObjectId(), temp.getDataType(), temp.getPeriodicity(), temp.getNumberOfDevices(), topic, temp.getMax(), temp.getMin());
	}

	private void setTimeDrivenSensor(String objectId, DataType messageType, int duration, int number_of_sensors,
									 String topic, String max, String min) {
		executorService.execute(new TDSensorDevice(objectId, messageType, max, min, duration, number_of_sensors, topic, latch));
	}

	private void setEventDrivenDevices(EDSensor event) {
		String topic = TopicGenerator.getSensorPublishTopic(event.getApiKey(), event.getDeviceId());
		String codePath = event.getSmartSpecSensor().getSmartSpecDataset().getCodePath();
		this.fileSystemStorageService.generateTargetLocation("smartspec", codePath);
		String sourceFilePath = fileSystemStorageService.getGeneratedFilesLocation().toFile().getAbsolutePath()
				+ File.separator
				+ Param.SENSOR_PREFIXFILE
				+ event.getSmartSpecSensor().getJsonId()+".csv";
		setEventDrivenSensor(event.getObjectId(), topic, sourceFilePath);
	}

	private void setEventDrivenSensor(String sensorType, String topic, String sourceFilePath) {
		executorService.execute(new EDSensorDevice(sensorType, topic, sourceFilePath, latch));
	}

	private void setActuatorDevices(Actuator act) {
		String topicPub = TopicGenerator.getSensorPublishTopic(act.getApiKey(),act.getDeviceId());
		String topicSub = TopicGenerator.getActuatorSubscribeTopic(act.getApiKey(),act.getDeviceId());
		String defaultState = act.getDefaultState();
		HashMap<String, String> commands = new HashMap<>();
		for (Command c: act.getCommands()) {
			commands.put(c.getName(), c.getState());
		}
		executorService.execute(new ActuatorDevice(act.getObjectId(), commands, defaultState, act.getPeriodicity(), topicPub, topicSub, latch));
		if (act.getSmartSpecSpace() != null) {
			topicPub = TopicGenerator.getCommandPublishTopic(act.getApiKey(),act.getDeviceId());
			String codePath = act.getSmartSpecSpace().getSmartSpecDataset().getCodePath();
			this.fileSystemStorageService.generateTargetLocation("smartspec", codePath);
			String sourceFilePath = fileSystemStorageService.getGeneratedFilesLocation().toFile().getAbsolutePath()
					+ File.separator
					+ Param.SPACE_PREFIXFILE
					+ act.getSmartSpecSpace().getJsonId()+".csv";
			executorService.execute(new EDCommandDevice(commands, defaultState, act.getPeriodicity(), topicPub, sourceFilePath, latch));
		}
	}

	private void setMobileSensorsDevice(MobileSensor mobileSensor) {
		String topic = TopicGenerator.getSensorPublishTopic(mobileSensor.getApiKey(), mobileSensor.getDeviceId());
		String codePath = mobileSensor.getSumoTrace().getCodePath();
		this.fileSystemStorageService.generateTargetLocation("sumo", codePath);
		String sourceFilePath = String.valueOf(fileSystemStorageService.getFileNameLocation(mobileSensor.getSumoTrace().getFileName().toString()));
		executorService.execute(new MobileSensorDevice(mobileSensor.getDeviceId(),mobileSensor.getMobileId(),mobileSensor.getPeriodicity(), "traffic", 1000, 1, topic, sourceFilePath,latch));
	}

	private int calculateNumThreads(Set<Device> devices) {
		int nThreads = 0;
		for (Device device : devices) {
			int nInstances = device.getInstances();
			for (Actuator actuator : device.getActuators()) {
				nThreads+=nInstances;
			}
			for (TDSensor tdSensor : device.getTdSensors()) {
				nThreads+=nInstances;
			}
			for (EDSensor edSensor : device.getEdSensors()) {
				nThreads+=nInstances;
			}
			for (MobileSensor mobileSensor : device.getMobileSensors()) {
				nThreads+=nInstances;
			}
		}
		return nThreads;
	}
	public void playMQTTProvider(Set<Device> devices, FileSystemStorageService fileSystemStorageService) {

		this.fileSystemStorageService = fileSystemStorageService;

		RandomController.setSeed();

		int nThreads = calculateNumThreads(devices);
		executorService = Executors.newFixedThreadPool(nThreads);
		latch = new CountDownLatch(1);

		String deviceId;
		String apiKey;

		for (Device device : devices) {
			apiKey = device.getDeviceGroup().getApiKey();
			for (int i = 0; i < device.getInstances(); i++) {
				deviceId = device.getDeviceId()+"I"+i;
				for (Actuator actuator : device.getActuators()) {
					actuator.setDeviceId(deviceId);
					actuator.setApiKey(apiKey);
					actuator.setNumberOfDevices(1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			                }
					setActuatorDevices(actuator);//, executorService, latch);
				}
				for (TDSensor tdSensor : device.getTdSensors()) {
					tdSensor.setDeviceId(deviceId);
					tdSensor.setApiKey(apiKey);
					tdSensor.setNumberOfDevices(1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setTimeDrivenDevices(tdSensor);
				}
				for (EDSensor edSensor : device.getEdSensors()) {
					edSensor.setDeviceId(deviceId);
					edSensor.setApiKey(apiKey);
					//edSensor.setNumberOfDevices(1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setEventDrivenDevices(edSensor);
				}
				for (MobileSensor mobileSensor : device.getMobileSensors()) {
					mobileSensor.setDeviceId(deviceId);
					mobileSensor.setApiKey(apiKey);
					mobileSensor.setNumberOfDevices(1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setMobileSensorsDevice(mobileSensor);
				}
			}
		}

		try {
			latch.await(Param.time_of_experiment, TimeUnit.SECONDS); // main thread is waiting on CountDownLatch to finish
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		latch.countDown();


		System.out.println("All threads finished!");

		//IdController.resetIds();
	}

	public void abortDevices() {
		latch.countDown();
		executorService.shutdownNow();
	}

}
