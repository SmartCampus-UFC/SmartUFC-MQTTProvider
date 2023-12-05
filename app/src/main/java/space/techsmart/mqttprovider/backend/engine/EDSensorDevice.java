package space.techsmart.mqttprovider.backend.engine;

import org.eclipse.paho.client.mqttv3.MqttException;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EDSensorDevice extends GenericDevice {

	private List<EDSensorEntry> edSensorEntries = new ArrayList<>();
	IdController idController = new IdController();
	private String sourceFilePath;

	public EDSensorDevice(String sensorType, String topic, String sourceFilePath, CountDownLatch latch) {
		super(sensorType, topic, null, latch);
		this.idController.setId();
		//this.nSensors = number_of_sensors;
		this.sourceFilePath = sourceFilePath;
	}

	public List<EDSensorEntry> mapCSV(String csvFilePath) {
		List<EDSensorEntry> list;
		File inputF = new File(csvFilePath);
		InputStream inputFS = null;
		try {
			inputFS = new FileInputStream(inputF);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
			list = br.lines().skip(1).map(csv2Obj).collect(Collectors.toList());
			br.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	private Function<String, EDSensorEntry> csv2Obj = (line) -> {
		String[] record = line.split(",");// This can be delimiter which
		EDSensorEntry entry = new EDSensorEntry();
		if (record[0] != null && record[0].trim().length() > 0) {
			entry.setTimestamp(Long.valueOf(record[0]));
		}
		if (record[1] != null && record[1].trim().length() > 0) {
			entry.setValue(record[1]);
		}
		return entry;
	};

	@Override
	public void run() {
		this.initializeSensor();
		try {
			this.publish();
		} catch (IOException ex) {
			Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void initializeSensor() {
		edSensorEntries = mapCSV(sourceFilePath);
	}

	private String setSensorInfo(String value) {
		String msg = key + "|" + value;
		return msg;
	}

	public void publish() throws IOException {
		long previousTimestamp = edSensorEntries.get(0).getTimestamp();
		for (EDSensorEntry edSensorEntry : edSensorEntries) {
			if (client == null) {
				this.connectMQTT();
			}
			String m = this.setSensorInfo(edSensorEntry.getValue());
			Long timeToSleep = edSensorEntry.getTimestamp() - previousTimestamp;
			previousTimestamp = edSensorEntry.getTimestamp();
			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				client.publish(this.topicPub, m.getBytes(), Param.qos, false);
			} catch (MqttException ex) {
				Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (latch.getCount() == 0) break;
		}
		try {
			client.disconnect(); // problema?
		} catch (MqttException ex) {
			Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
