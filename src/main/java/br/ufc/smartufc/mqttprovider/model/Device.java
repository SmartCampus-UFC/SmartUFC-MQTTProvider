package br.ufc.smartufc.mqttprovider.model;

import java.util.Objects;

public abstract class Device {
	protected String type;
	protected String apiKey;
	protected String deviceId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "{" +
			" type='" + getType() + "'" +
			", apiKey='" + getApiKey() + "'" +
			", deviceId='" + getDeviceId() + "'" +
			"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Device)) {
			return false;
		}
		Device device = (Device) o;
		return Objects.equals(type, device.type) && Objects.equals(apiKey, device.apiKey) && Objects.equals(deviceId, device.deviceId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, apiKey, deviceId);
	}

}
