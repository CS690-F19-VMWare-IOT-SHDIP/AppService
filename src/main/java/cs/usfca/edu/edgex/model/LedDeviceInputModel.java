package cs.usfca.edu.edgex.model;

import com.google.gson.Gson;

public class LedDeviceInputModel extends DeviceModel {
	private int pin;
	private boolean state;
	private transient Gson gson;
	
	private class State {
		private int addr;
		private boolean state;

		public State(int addr, boolean state) {
			this.addr = addr;
		    this.state = state;
		}
	}
	
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
		return gson.toJson(new State(this.pin, this.state));
	}
}
