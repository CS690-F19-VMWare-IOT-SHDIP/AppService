package cs.usfca.edu.edgex.model;

public class LEDModel extends DeviceModel {
	
	/*
	 * pin number on the raspberry pi
	 */
	private int pinNumber;
	
	public int getPinNumber() {
		return this.pinNumber;
	}
	
	public void setPinNumber(int pin) {
		this.pinNumber = pin;
	}
}
