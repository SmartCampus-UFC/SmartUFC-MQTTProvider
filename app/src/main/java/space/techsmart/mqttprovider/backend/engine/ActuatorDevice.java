package space.techsmart.mqttprovider.backend.engine;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActuatorDevice extends GenericDevice {

	private String state;
	private String response;
	private boolean hasResponse;
	private HashMap<String,String> commands;

	public ActuatorDevice(String sensorType, HashMap<String,String> commands, String defaultState, int duration, String topicPub, String topicSub,
			CountDownLatch latch) {
		super(sensorType, duration, topicPub, topicSub, latch);
		this.duration = duration * 1000;
		this.commands = commands;
		this.state = defaultState;
		this.hasResponse = false;		
	}

	@Override
	public void run() {
		try {
			this.start();
		} catch (IOException ex) {
			Logger.getLogger(ActuatorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private String setSensorInfo() {
		String msg = this.key + "|" + this.state;
		return msg;
	}

	public void start() throws IOException {
		String m;
		try {
			if (client == null)
				this.connectMQTT();				
			try {
				Thread.sleep(RandomController.nextInt(this.duration));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			client.subscribe(this.topicSub);							
			do {
				if (!this.hasResponse) {
					m = this.setSensorInfo();
				} else {
					m = this.response;
					this.hasResponse = false;
				}					
				client.publish(this.topicPub, m.getBytes(), Param.qos, false);
				try {					
					Thread.sleep(this.duration);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			} while (latch.getCount() != 0);
		} catch (MqttException ex) {
			//ex.printStackTrace();
			Logger.getLogger(ActuatorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			client.disconnect();
		} catch (MqttException ex) {
			Logger.getLogger(ActuatorDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
		//latch.countDown();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String response;
		String msg = new String(message.getPayload());
		if (msg.contains("@") && msg.contains("|")) {
			String[] cmd = msg.split("\\|");
			String key = cmd[0].split("\\@")[1];			
			System.out.println("Command:\t" + key);
			if (commands.containsKey(key)) {
				this.state = commands.get(key);
				response = msg + " " + key + " OK";
			} else {
				response = msg + " " + key + " NOT_EXIST";
			}
		} else {
			response = msg + " | NOT_EXIST";
		}
		System.out.println("Response: \t" + response);
		this.response = response;
		this.hasResponse = true;
	}
}