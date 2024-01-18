package space.techsmart.mqttprovider.backend.engine;


import org.eclipse.paho.client.mqttv3.MqttException;
import space.techsmart.mqttprovider.backend.utils.DataType;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TDSensorDevice extends GenericDevice {

	private final int nSensors;
	private LinkedList<Sensor> sensor = new LinkedList<>();
	IdController idController = new IdController();

	public TDSensorDevice(String sensorType, DataType messageType, String max, String min, int duration,
			int number_of_sensors, String topic, CountDownLatch latch) {
		super(sensorType, messageType, max, min, duration, topic, null, latch);
		this.idController.setId();
		this.duration = duration * 1000;
		this.nSensors = number_of_sensors;
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

	private void initializeSensors() {
		for (int i = 0; i < nSensors; i++) {
			Sensor s1 = new Sensor();
			s1.setDuration(duration);
			s1.setId(IdController.getId(key));
			s1.setStartSend(RandomController.nextInt(s1.getDuration()));
			sensor.add(s1);
		}

	}

	private String setSensorInfo(String name, int idSensor) {

		String msg = "";

		msg += key + "|";
		if (!(messageType.equals("booleanText"))) {
			if (max == null)
				msg += getRandomData(messageType);
			else
				msg += getRandomData(messageType, max, min);
		} else {
			msg += (RandomController.nextInt() % 2 == 0) ? max : min;
		}
		return msg;
	}

	public void publish() throws IOException {
		if (client == null) {
			this.connectMQTT();
		}
		// int numberOfMsg = 0;
		String m = this.setSensorInfo(key, 1);
		Sensor temp, temp2;
		int timeToSleep = 1;
		try {
			if (client == null) {
				this.connectMQTT();
			}
			Collections.sort(sensor);
			temp = sensor.peek();

			try {
				Thread.sleep(temp.getStartSend());
				} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			do {
				if (!sensor.isEmpty())
					temp = (Sensor) sensor.remove();

				client.publish(this.topicPub, m.getBytes(), Param.qos, false);

				if (temp != null) {
					timeToSleep = temp.getStartSend();
					temp.setStartSend(temp.getStartSend() + duration);
					sensor.add(temp);
				}
				if (!sensor.isEmpty()) {
					temp2 = (Sensor) sensor.peek();
					timeToSleep = temp2.getStartSend() - timeToSleep;
					m = this.setSensorInfo(key, temp2.getId());
				}
				try {
					Thread.sleep(timeToSleep);
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
			Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
		//latch.countDown();
	}

}