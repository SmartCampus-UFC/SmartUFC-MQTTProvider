package space.techsmart.mqttprovider.backend.engine;

import org.eclipse.paho.client.mqttv3.MqttException;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobileSensorDevice extends GenericDevice {
	
	private final int nSensors;
	private LinkedList<Sensor> sensor = new LinkedList<>();
	IdController idController = new IdController();
	private String deviceId;
	private Vehicle vehicle;

	private String sourceFilePath;

	public MobileSensorDevice(String deviceId, String mobileId, int periodicity, String sensorType, int duration, int number_of_sensors, String topic,
							  String sourceFilePath, CountDownLatch latch) {
		super(sensorType, duration, topic, null, latch);
		this.deviceId = deviceId;
		//Can use periodicity as step
		this.sourceFilePath = sourceFilePath;
		this.vehicle = new Vehicle(mobileId,20,periodicity,sourceFilePath);
		this.duration = duration * 1000;
		this.nSensors = number_of_sensors;

		//Class the get the data of the mobile sensor.

	}

	
	private void initializeSensors() {
		for (int i = 0; i < nSensors; i++) {
			Sensor s1 = new Sensor();
			s1.setDuration(duration);
			s1.setId(IdController.getId(key));
			s1.setStartSend(RandomController.nextInt(s1.getDuration()));
			sensor.add(s1);
		}

	}

	@Override
	public void run() {
		this.initializeSensors();
		try {
			this.publish();
		} catch (IOException ex) {
			Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
	}



	public void publish() throws IOException {
	    if (client == null) {
	        this.connectMQTT();
	    }

	    int timeToSleep = 1;
	    
	    try {
			try {
			    Thread.sleep(timeToSleep);
			} catch (InterruptedException e1) {
			    e1.printStackTrace();
			}
	
			do {
			    // Update the vehicle's coordinates here
				vehicle.updateCoordinates();
			    if (vehicle.isPresent() == true) {
			        
			        double x = vehicle.getX();
			        double y = vehicle.getY();
			        String m1 = "long|" + x+"|lat|" + y;
			        System.out.println(m1); // Output the coordinates
					client.publish(this.topicPub, m1.getBytes(), Param.qos, false);
			    }
			    try {
			        Thread.sleep(timeToSleep);
			        Thread.sleep(2000); // Sleep for 5 seconds (1000 milliseconds)
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
			} while (latch.getCount() != 0);
	    } catch (MqttException ex) {
			Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}

	    try {
	        client.disconnect();
	    } catch (MqttException ex) {
	        Logger.getLogger(MobileSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
}
