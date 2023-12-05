package space.techsmart.mqttprovider.backend.engine;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.*;
import space.techsmart.mqttprovider.backend.utils.DataType;
import space.techsmart.mqttprovider.backend.utils.Param;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public abstract class GenericDevice implements Runnable, MqttCallback {
	protected String key, topicPub, topicSub, max, min;
	protected int duration;
	protected CountDownLatch latch;
	protected MqttClient client;
	protected MqttConnectOptions options;
	protected Random fullRand = new Random();
	protected DataType messageType;
	protected boolean isAbort = false;

	public GenericDevice(String key, int duration, String topicPub, String topicSub, CountDownLatch latch) {
		this.key = key;
		this.topicPub = topicPub;
		this.topicSub = topicSub;
		this.duration = duration;
		this.latch = latch;
		fullRand.setSeed(System.currentTimeMillis());
	}

	// EdSensorDevice
	public GenericDevice(String key, String topicPub, String topicSub, CountDownLatch latch) {
		this.key = key;
		this.topicPub = topicPub;
		this.topicSub = topicSub;
		this.latch = latch;
	}

	public GenericDevice(String key, DataType messageType, String max, String min, int duration,
						 String topicPub, String topicSub, CountDownLatch latch) {
		this.key = key;
		this.topicPub = topicPub;
		this.topicSub = topicSub;
		this.duration = duration;
		this.latch = latch;
		this.messageType = messageType;
		this.max = max;
		this.min = min;
		fullRand.setSeed(System.currentTimeMillis());
	}

	/*public GenericDevice(String sensorType, String topicPub, String topicSub, CountDownLatch latch) {
		this.sensorType = sensorType;
		this.topicPub = topicPub;
		this.topicSub = topicSub;
		this.latch = latch;
		fullRand.setSeed(System.currentTimeMillis());
	}*/

	protected String getRandomData(DataType type) {
		String msg;

		switch (type) {
			case INT:
				msg = String.valueOf(RandomController.nextInt());
				break;
			case FLOAT:
				msg = String.valueOf(RandomController.nextFloat());
				break;
			case BOOLEAN:
				msg = String.valueOf(RandomController.nextBoolean());
				break;
			case CHAR:
				do {
					msg = String.valueOf(RandomController.nextChar());
				} while (msg.charAt(0) == '=' || msg.charAt(0) == ';');

				break;
			default:
				msg = "invalid";
				break;
		}
		return msg;
	}

	protected String getRandomData(DataType type, String max, String min) {
		String msg;

		switch (type) {
			case INT:
				int maxInt = Integer.parseInt(max), minInt = Integer.parseInt(min);
				if (maxInt == minInt)
					msg = max + "";
				else if ((maxInt - minInt) > 0)
					msg = String.valueOf(RandomController.nextInt(maxInt - minInt) + minInt);
				else if (minInt == Integer.MIN_VALUE)
					msg = String.valueOf(RandomController.nextInt(maxInt));
				else
					msg = String.valueOf(RandomController.nextInt());
				break;
			case FLOAT:
				float maxF = Float.parseFloat(max), minF = Float.parseFloat(min);
				if (maxF == minF)
					msg = max + "";
				msg = String.valueOf(RandomController.nextFloat(maxF, minF));
				break;
			case BOOLEAN:
				msg = String.valueOf(RandomController.nextBoolean()); // does no
																		// apply
				break;
			case CHAR:
				char maxC = max.charAt(0), minC = min.charAt(0);
				if (maxC == minC)
					msg = max + "";
				do {
					msg = String.valueOf(RandomController.nextChar(maxC, minC));
				} while (msg.charAt(0) == '=' || msg.charAt(0) == ';');
				break;
			default:
				msg = "invalid";
				break;
		}
		return msg;
	}

	void connectMQTT() {
		if (client == null) {
			try {
				client = new MqttClient(Param.address, key + Param.replication + fullRand.nextInt(9999999),
						null);
				client.setCallback(this);
				options = new MqttConnectOptions();
				options.setUserName("admin");
				options.setPassword("password".toCharArray());
				client.connect(options);
			} catch (MqttException e) {
				Logger.getLogger(GenericDevice.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

	public void connectionLost(Throwable msg) {
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String time = new Date(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" + time + "  Topic:\t" + topic + "  Message:\t" + new String(message.getPayload())
				+ "  QoS:\t" + message.getQos());

	}	

	public boolean isAbort() {
		return isAbort;
	}

	public void setAbort(boolean isAbort) {
		this.isAbort = isAbort;
	}

}
