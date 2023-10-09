package br.ufc.smartufc.mqttprovider.code;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import br.ufc.smartufc.mqttprovider.util.Param;
import br.ufc.smartufc.mqttprovider.util.MobileSensor.Vehicle;

public class MobileSensorDevice extends GenericDevice{
	
	private final int nSensors;
	private LinkedList<Sensor> sensor = new LinkedList<>();
	IdController idController = new IdController();
	private String deviceId;
	private Vehicle vehicle;

	public MobileSensorDevice(String deviceId, String mobileId, int periodicity,String sensorType, String[] messageType, int duration, int number_of_sensors, String topic, 
			CountDownLatch latch) {
		super(sensorType, messageType, duration, topic, null, latch);
		this.deviceId = deviceId;
		//Can use periodicity as step
		this.vehicle = new Vehicle(mobileId,20,periodicity,"src/main/java/br/ufc/smartufc/mqttprovider/util/MobileSensor/ufc-cenario/osmWithStop.xml");
		this.duration = duration * 1000;
		this.nSensors = number_of_sensors;
		
		//Class the get the data of the mobile sensor.

	}

	
	private void initializeSensors() {
		System.out.println("A duração do sensor " + this.sensorType + " é " + this.duration);
		for (int i = 0; i < nSensors; i++) {
			Sensor s1 = new Sensor();
			s1.setDuration(duration);
			s1.setId(IdController.getId(sensorType));
			s1.setStartSend(RandomController.nextInt(s1.getDuration()));
			sensor.add(s1);
		}

	}

	@Override
	public void run() {
		System.out.println(
				sensorType + " sends a message  in each " + duration + " ms. We will generate " + nSensors + "\n");
		// random.setSeed(Param.seed);
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
				String m = "";
				m += "g|";//sensorType+ deviceId+ "g|";
				vehicle.updateCoordinates();
			    if (vehicle.isPresent() == true) {
			        
			        double x = vehicle.getX();
			        double y = vehicle.getY();
			        //m += "(" + x + "," + y + ")"; // Format coordinates as (x,y)
					m+="{\"type\": \"Point\", \"coordinates\": [" + x + ", " + y + "]}";
			        System.out.println(m); // Output the coordinates
			    }
			    client.publish(this.topicPub, m.getBytes(), Param.qos, false);
			    
			    try {
			        Thread.sleep(timeToSleep);
			        Thread.sleep(2000); // Sleep for 5 seconds (1000 milliseconds)
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
			} while (!(TimeControl.isDone()) && !isAbort);
	    } catch (MqttException ex) {
			Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}

	    try {
	        client.disconnect();
	    } catch (MqttException ex) {
	        Logger.getLogger(MobileSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    latch.countDown();
	}
}
