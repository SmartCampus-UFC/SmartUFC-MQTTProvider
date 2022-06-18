package br.ufc.smartufc.mqttprovider.model;

import java.util.Objects;

public class TDSensor extends Device {
	private int numberOfDevices;
	private int periodicity;
	private String[] data;
	private String[] max;
	private String[] min;

	public TDSensor() {

	}

	/*
	 * public TDSensor(String type, String topic, int numberOfDevices) {
	 * this.type = type;
	 * this.topic = topic;
	 * this.numberOfDevices = numberOfDevices;
	 * }
	 */

	/*
	 * public TDSensor(String type, String topic, int numberOfDevices, int
	 * periodicity,String[] data, String[] max, String[] min) {
	 * this.type = type;
	 * this.topic = topic;
	 * this.numberOfDevices = numberOfDevices;
	 * this.periodicity = periodicity;
	 * this.data = data;
	 * this.max = max;
	 * this.min = min;
	 * }
	 */

	public int getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(int periodicity) {
		this.periodicity = periodicity;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public String[] getMax() {
		return max;
	}

	public void setMax(String[] max) {
		this.max = max;
	}

	public String[] getMin() {
		return min;
	}

	public void setMin(String[] min) {
		this.min = min;
	}

	public int getNumberOfDevices() {
		return numberOfDevices;
	}

	public void setNumberOfDevices(int numberOfDevices) {
		this.numberOfDevices = numberOfDevices;
	}

	@Override
	public String toString() {
		return "{" +
				" numberOfDevices='" + getNumberOfDevices() + "'" +
				", periodicity='" + getPeriodicity() + "'" +
				", data='" + getData() + "'" +
				", max='" + getMax() + "'" +
				", min='" + getMin() + "'" +
				"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof TDSensor)) {
			return false;
		}
		TDSensor tDSensor = (TDSensor) o;
		return numberOfDevices == tDSensor.numberOfDevices && periodicity == tDSensor.periodicity
				&& Objects.equals(data, tDSensor.data) && Objects.equals(max, tDSensor.max)
				&& Objects.equals(min, tDSensor.min);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numberOfDevices, periodicity, data, max, min);
	}

}
