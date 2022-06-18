package br.ufc.smartufc.mqttprovider.model;

import java.util.HashMap;
import java.util.Objects;

public class Actuator extends Device {
	private HashMap<String, String> commands;
	private int periodicity;
	private String defaultState;

	public Actuator() {

	}

	public int getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(int periodicity) {
		this.periodicity = periodicity;
	}

	public HashMap<String, String> getCommands() {
		return this.commands;
	}

	public void setCommands(HashMap<String, String> commands) {
		this.commands = commands;
	}

	public String getDefaultState() {
		return this.defaultState;
	}

	public void setDefaultState(String defaultState) {
		this.defaultState = defaultState;
	}

	@Override
	public String toString() {
		return "{" +
				" commands='" + getCommands() + "'" +
				", periodicity='" + getPeriodicity() + "'" +
				", defaultState='" + getDefaultState() + "'" +
				"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Actuator)) {
			return false;
		}
		Actuator actuator = (Actuator) o;
		return Objects.equals(commands, actuator.commands) && periodicity == actuator.periodicity
				&& Objects.equals(defaultState, actuator.defaultState);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commands, periodicity, defaultState);
	}

}
