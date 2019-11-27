package cs.usfca.edu.edgex.device;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.LedDeviceInputModel;
import cs.usfca.edu.edgex.model.Model;

public class LedDevice implements Device<Boolean> {
	private LedDeviceInputModel input;
	private Gson gson;
	
	private static class State {
		private String states;
		
		public State(String states) {
			this.states = states;
		}

		public String getStates() {
			return states;
		}

		public void setStates(String states) {
			this.states = states;
		}
		
	}
	
	public LedDevice(Model input) throws InvalidInputException {
		if(input instanceof LedDeviceInputModel) {
			this.input = (LedDeviceInputModel)input;
			this.gson = new Gson();
		}
		else {
			throw new InvalidInputException("Wrong object passed in to LedDevice");
		}
	}

	@Override
	public Boolean get() {
		// Get data from Edgex
		// Convert To State
		// Convert State to single pin from input
		// return state for single pin.
		return null;
	}

	@Override
	public void set(Boolean val) {
		// update input state for pin
		// convert to State
		// send to Edgex.
	}

	@Override
	public DeviceType getDeviceType() {
		return DeviceType.VIRTUAL;
	}

	@Override
	public Object getDevice() {
		return input;
	}

	@Override
	public boolean equals(Device<?> device) {
		if(device.getDevice() instanceof LedDeviceInputModel) {
			LedDeviceInputModel received = (LedDeviceInputModel) device.getDevice();
			if(received.isState() == input.isState() && received.getPin() == input.getPin()) {
				return true;
			}
		}
		return false;
	}

	
	

}
