package br.ufc.smartufc.mqttprovider.code;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import br.ufc.smartufc.mqttprovider.model.Actuator;
import br.ufc.smartufc.mqttprovider.model.EDSensor;
import br.ufc.smartufc.mqttprovider.model.TDSensor;
import br.ufc.smartufc.mqttprovider.util.Param;
import br.ufc.smartufc.mqttprovider.util.TopicGenerator;

public class MqttPubSub implements Runnable {

	// private final Random random = new Random();
	private TDSensor[] timeSensors;
	private EDSensor[] eventSensors;
	private Actuator[] actuators;
	private ArrayList<Thread> threads = new ArrayList<Thread>();

	// TODO permitir instanciar até x sensores genéricos
	// setar max e min para cada valor de dado de sensor
	public MqttPubSub() {
	}

	public MqttPubSub(TDSensor[] timeSensors, EDSensor[] eventSensors) {
		// System.out.println("construtor");
		// this.timeSensors = timeSensors;
		// this.eventSensors = eventSensors;
	}

	public MqttPubSub(EDSensor[] eventSensors) {
		// this.eventSensors = eventSensors;
	}

	public MqttPubSub(TDSensor[] timeSensors) {
		// this.timeSensors = timeSensors;
	}

	@Override
	public void run() {
		start();
	}

	private void setTemplateTimeDrivenSensor(String sensorType, String topic, int number_of_sensors, CountDownLatch l) {
		TDSensorDevice newSensor;
		newSensor = new TDSensorDevice(sensorType, number_of_sensors, topic, l);
		Thread thread = new Thread(newSensor);
		threads.add(thread);
		thread.start();
	}

	/*
	 * private void setUID() {
	 * MqttSubscribe sub = new MqttSubscribe();
	 * Thread threadSub = new Thread(sub);
	 * threadSub.start();
	 * }
	 */

	private void setTimeDrivenDevices(TDSensor temp, CountDownLatch l) {
		String topic = TopicGenerator.getPublishTopic(temp.getApiKey(), temp.getDeviceId());
		switch (temp.getType()) {
			case "air":
				setTemplateTimeDrivenSensor("air", topic, temp.getNumberOfDevices(), l);
				break;
			case "waste":
				setTemplateTimeDrivenSensor("waste", topic, temp.getNumberOfDevices(), l);
				break;
			case "noise":
				setTemplateTimeDrivenSensor("noise", topic, temp.getNumberOfDevices(), l);
				break;
			case "structural":
				setTemplateTimeDrivenSensor("structural", topic, temp.getNumberOfDevices(), l);
				break;
			case "traffic":
				setTemplateTimeDrivenSensor("traffic", topic, temp.getNumberOfDevices(), l);
				break;
			default:
				setTimeDrivenSensor(temp.getType(), temp.getData(), temp.getPeriodicity(), temp.getNumberOfDevices(),
						topic, temp.getMax(), temp.getMin(),
						l);
				break;
		}
	}

	private void setEventDrivenDevices(EDSensor event, CountDownLatch l) {
		String topic = TopicGenerator.getPublishTopic(event.getApiKey(), event.getDeviceId());
		if (event.getType().equals("lightController")) {
			Long temp = ((Param.time_of_experiment * 3) / 4);
			setTemplateEventDrivenSensor(event.getType(), topic, event.getLambda(), temp.intValue(), l);
		} else {
			Long temp = ((Param.time_of_experiment * 3) / 4);
			if (event.getData()[0].equals("variable")) {
				setEventDrivenSensor(event.getType(), topic, event.getData(), event.getLambda(),
						temp.intValue(), event.getMode(), event.getMax(), event.getMin(), l);
			} else {
				setEventDrivenSensor(event.getType(), topic, event.getData(), event.getLambda(),
						((int) Param.time_of_experiment - 1), event.getMode(), event.getMax(), event.getMin(), l);
			}

		}
	}

	private void setActuatorDevices(Actuator act, CountDownLatch latch) {
		String topicPub = TopicGenerator.getPublishTopic(act.getApiKey(),act.getDeviceId());
		String topicSub = TopicGenerator.getSubscribeTopic(act.getApiKey(),act.getDeviceId());
		ActuatorDevice newActuator = new ActuatorDevice(act.getType(), act.getCommands(), act.getDefaultState(), act.getPeriodicity(), topicPub, topicSub, latch);				
		Thread thread = new Thread(newActuator);
		threads.add(thread);
		thread.start();
	}

	// Event Driven Sensors, each thread represents a event (ex: a football
	// match)
	// Constructor EventDrivenSensor(String sensorType,int idSensor, double
	// lambda, int duration, latch)
	// DURATION IN MINUTES
	private void setTemplateEventDrivenSensor(String sensorType, String topic, double lambda, int duration,
			CountDownLatch l) {
		EDSensorDevice newSensor;
		newSensor = new EDSensorDevice(sensorType, lambda, duration, topic, l);
		Thread thread = new Thread(newSensor);
		threads.add(thread);
		thread.start();

	}

	private void setTimeDrivenSensor(String sensorType, String[] messageType, int duration, int number_of_sensors,
			String topic, String[] max, String[] min, CountDownLatch latch) {
		System.out.println("max: " + max[0] + " min: " + min[0]);
		TDSensorDevice newSensor = new TDSensorDevice(sensorType, messageType, max, min, duration,
				number_of_sensors, topic,
				latch);
		Thread thread = new Thread(newSensor);
		threads.add(thread);
		thread.start();
	}

	// private static void setTimeDrivenSensor(String sensorType, String[]
	// messageType, int duration, int number_of_sensors,
	// String topic,CountDownLatch latch) {
	// TimeDrivenSensor newSensor = new TimeDrivenSensor(sensorType, messageType,
	// duration, number_of_sensors, topic,
	// latch);
	// Thread thread = new Thread(newSensor);
	// threads.add(thread);
	// thread.start();
	// }
	private void setEventDrivenSensor(String sensorType, String topic, String[] messageType, double lambda,
			int duration, String mode, String[] max, String[] min, CountDownLatch latch) {
		EDSensorDevice newSensor = new EDSensorDevice(sensorType, messageType, max, min, lambda, mode, duration,
				topic, latch);
		Thread thread = new Thread(newSensor);
		threads.add(thread);
		thread.start();
	}

	public void start() {
		// System.out.println(Param.experiment_num + " is going to start in " +
		// Param.time_between_exp + "s");
		RandomController.setSeed();
		TimeControl.setTimeOfExperiment(Param.time_of_experiment);
		TimeControl.startTime();
		CountDownLatch latch = new CountDownLatch(1);
		if (timeSensors != null) {
			TDSensor temp = new TDSensor();
			for (int i = 0; i < timeSensors.length; i++) {
				temp = timeSensors[i];
				setTimeDrivenDevices(temp, latch);
			}
		}
		if (eventSensors != null) {
			EDSensor event = new EDSensor();
			for (int i = 0; i < eventSensors.length; i++) {
				event = eventSensors[i];
				setEventDrivenDevices(event, latch);
			}
		}
		if (actuators != null) {
			Actuator actuator = new Actuator();
			for (int i = 0; i < actuators.length; i++) {
				actuator = actuators[i];
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setActuatorDevices(actuator, latch);
			}
		}
		try {
			latch.await(); // main thread is waiting on CountDownLatch to finish
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
		}

		System.out.println("Todos as Threads já foram instanciandos");
		while (TimeControl.getTime() <= TimeControl.getTimeOfExperiment()) {
			System.out.println("waiting for experiment to end " + TimeControl.getTime() + ", ends "
					+ TimeControl.getTimeOfExperiment());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// IdController.resetIds();
			// MessageArray.clean();
		}
		// System.out.println("RESETANDO EXPERIMENTO");
		IdController.resetIds();
		// MessageArray.clean();
	}

	@SuppressWarnings("deprecation")
	public void abort() {
		// TimeControl.s
		TimeControl.setExperimentStarted(false);
		TimeControl.setTimeControl(true);
		for (Thread thread : threads) {
			IdController.resetIds();
			thread.stop();
		}

	}

	public TDSensor[] getTimeSensors() {
		return timeSensors;
	}

	public void setTimeSensors(TDSensor[] timeSensors) {
		this.timeSensors = timeSensors;
	}

	public EDSensor[] getEventSensors() {
		return eventSensors;
	}

	public void setEventSensors(EDSensor[] eventSensors) {
		this.eventSensors = eventSensors;
	}

	public Actuator[] getActuators() {
		return this.actuators;
	}

	public void setActuators(Actuator[] actuators) {
		this.actuators = actuators;
	}

}
