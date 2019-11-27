package cs.usfca.edu.edgex.model;

import com.google.gson.Gson;

public class LedDeviceInputModel extends Model {
	private int pin;
	private boolean state;
	private Gson gson;
	
	public LedDeviceInputModel() {
		this.gson = new Gson();
	}
	
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return gson.toJson(this);
	}
}
