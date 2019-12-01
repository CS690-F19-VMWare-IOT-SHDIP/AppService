package cs.usfca.edu.edgex.device.physicaldevices;

import org.json.JSONObject;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.apis.deviceapis.DeviceHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.edgexclient.DeviceEvent;
import cs.usfca.edu.edgex.edgexclient.EdgeXClient;
import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.model.FanModel;

public class FanDevice implements PhysicalDevice<Boolean> {
	
	private DeviceModel fan;
	private Boolean value;
	
	public FanDevice(DeviceModel fan) throws InvalidInputException {
		if(fan instanceof FanModel) {
			this.fan = fan;
			this.value = false;
		}
		else {
			throw new InvalidInputException("Wrong object passed in to SlackDevice");
		}
	}
	

	@Override
	public Boolean get() {
		String deviceID = DeviceHandlers.getPhysicalDeviceID(this);
		String responseData = EdgeXClient.get(this.fan);
		System.out.println("------------>Response Data: " + responseData);
		DeviceEvent reading = new Gson().fromJson(responseData.toString(), DeviceEvent.class);
		
		this.value = (reading.getReadings().get(0).getValue().equals("true")) ? true : false;
		return value;
	}

	@Override
	public void set(Boolean value) {
		String deviceID = DeviceHandlers.getPhysicalDeviceID(this);

		String val = (value == true) ? "true" : "false";
		JSONObject putBody = new JSONObject().put(this.fan.getResourceName(), val);
		String responseData = EdgeXClient.put(this.fan, putBody.toString());
		if(responseData == null) {
			System.out.println("----------------> response null");
		}
		this.value = value;
	}

	@Override
	public DeviceType getDeviceType() {
		return fan.getDeviceType();
	}

	@Override
	public Object getDevice() {
		return fan;
	}

	@Override
	public boolean equals(Device<?> device) {
		if(device.getDevice() instanceof DeviceModel) {
			DeviceModel received = (DeviceModel) device.getDevice();
			if(received.getDeviceName().equals(fan.getDeviceName()) && received.getDeviceType() == fan.getDeviceType() 
					&& received.getResourceName().equals(fan.getResourceName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public DeviceModel getDeviceModel() {
		return fan;
	}

}
