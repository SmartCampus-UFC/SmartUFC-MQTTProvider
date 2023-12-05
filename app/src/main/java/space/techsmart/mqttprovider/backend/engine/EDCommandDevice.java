package space.techsmart.mqttprovider.backend.engine;

import org.eclipse.paho.client.mqttv3.MqttException;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EDCommandDevice extends GenericDevice {

	private List<EDCommandEntry> edCommandEntries = new ArrayList<>();
	IdController idController = new IdController();
	private String sourceFilePath;
	private String state;
	private String deviceName;
	private HashMap<String,String> commands;

	public EDCommandDevice(HashMap<String,String> commands, String defaultState, int duration, String topic, String sourceFilePath, CountDownLatch latch) {
		super(null, duration, topic, null, latch);
		this.idController.setId();
		this.duration = duration * 1000;
		this.sourceFilePath = sourceFilePath;
		this.state = defaultState;
		this.commands = commands;
		this.deviceName = topic.split("/")[2];
	}

	public List<EDCommandEntry> mapCSV(String csvFilePath) {
		List<EDCommandEntry> list;
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

	private Function<String, EDCommandEntry> csv2Obj = (line) -> {
		String[] record = line.split(",");// This can be delimiter which
		EDCommandEntry entry = new EDCommandEntry();
		if (record[1] != null && record[1].trim().length() > 0) {
			entry.setStartTimestamp(Long.valueOf(record[1]));
		}
		if (record[2] != null && record[2].trim().length() > 0) {
			entry.setEndTimestamp(Long.valueOf(record[2]));
		}
		return entry;
	};

	@Override
	public void run() {
		this.initializeCommander();
		try {
			this.publish();
		} catch (IOException ex) {
			Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void initializeCommander() {
		edCommandEntries = mapCSV(sourceFilePath);
	}

	private String setCommandInfo() {
		String msg = this.deviceName + "@" + getNextCommand() + "|";
		return msg;
	}

	private String getNextCommand() {
		for (Map.Entry<String, String> entry : commands.entrySet()) {
			if (!state.equals(entry.getValue())) {
				this.state = entry.getValue();
				return entry.getKey();
			}
		}
		return null;
	}

	public void publish() throws IOException {
		String m;
		Long timeToSleep;
		try {
			Thread.sleep(RandomController.nextInt(this.duration));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (EDCommandEntry edCommandEntry : edCommandEntries) {
			if (client == null) {
				this.connectMQTT();
			}
			timeToSleep = edCommandEntry.getEndTimestamp() - edCommandEntry.getStartTimestamp();
			try {
				m = this.setCommandInfo();
				client.publish(this.topicPub, m.getBytes(), Param.qos, false);
				Thread.sleep(timeToSleep);
				m = this.setCommandInfo();
				client.publish(this.topicPub, m.getBytes(), Param.qos, false);
			} catch (MqttException ex) {
				Logger.getLogger(TDSensorDevice.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
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
