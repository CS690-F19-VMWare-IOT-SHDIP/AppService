package cs.usfca.edu.edgex.device.physicaldevices;

import org.json.JSONObject;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.apis.deviceapis.DeviceHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.edgexclient.EdgeXClient;
import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.model.LedDeviceInputModel;
import cs.usfca.edu.edgex.model.Model;

public class LedDevice implements PhysicalDevice<Boolean> {
	private LedDeviceInputModel input;
	private Gson gson;
	private State state;
	
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
	
	public LedDevice(DeviceModel input) throws InvalidInputException {
		if(input instanceof LedDeviceInputModel) {
			this.input = (LedDeviceInputModel)input;
			this.gson = new Gson();
			this.state = new State(input.toString());
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
		String deviceID = DeviceHandlers.getPhysicalDeviceID(this);
		//JSONObject putBody = new JSONObject().put(this.input.getResourceName(), this.state.getStates());
		//String responseData = EdgeXClient.put(this.input, deviceID, putBody.toString());
		String jsonStr = gson.toJson(state);
		System.out.println("Sending following state data to edgex for LED: " + jsonStr);
		String responseData = EdgeXClient.put(this.input, deviceID, jsonStr);
	}

	@Override
	public DeviceType getDeviceType() {
		return input.getDeviceType();
	}

	@Override
	public Object getDevice() {
		return input;
	}
	
	/**
	 * Returns physical device input model.
	 * @return DeviceModel
	 */
	@Override
	public DeviceModel getDeviceModel() {
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
