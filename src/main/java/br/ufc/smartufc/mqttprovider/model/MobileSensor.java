package br.ufc.smartufc.mqttprovider.model;

public class MobileSensor extends Device{
	
	private String mobileId;
	private int periodicity;
	
	public String getMobileId(){
		return this.mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public int getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(int periodicity) {
		this.periodicity = periodicity;
	}
    
}
