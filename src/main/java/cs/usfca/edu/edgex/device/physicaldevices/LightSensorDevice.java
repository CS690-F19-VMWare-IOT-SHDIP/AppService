package cs.usfca.edu.edgex.device.physicaldevices;

import org.json.JSONObject;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.apis.deviceapis.DeviceHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.edgexclient.DeviceEvent;
import cs.usfca.edu.edgex.edgexclient.EdgeXClient;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.model.LightSensorModel;

public class LightSensorDevice implements PhysicalDevice<Integer> {
	
	private DeviceModel ledSensor;
	private int value;
	
	public LightSensorDevice(DeviceModel ledSensor) {
		if(ledSensor instanceof LightSensorModel) {
			this.ledSensor = ledSensor;
			this.value = 0;
		}
	}

	@Override
	public Integer get() {
		String deviceID = DeviceHandlers.getPhysicalDeviceID(this);
		String responseData = EdgeXClient.get(this.ledSensor, deviceID);
		DeviceEvent reading = new Gson().fromJson(responseData.toString(), DeviceEvent.class);
		this.value = reading.getReadings().get(0).getValue().equals("1") ? 1 : 0;
		return value;
	}

	@Override
	public void set(Integer val) {
		String deviceID = DeviceHandlers.getPhysicalDeviceID(this);
		if (value != val) {
			JSONObject putBody = new JSONObject().put(this.ledSensor.getResourceName(), val);
			String responseData = EdgeXClient.put(this.ledSensor, deviceID, putBody.toString());
			if (responseData != null) {
				this.value = val;
			}
		}
	}

	@Override
	public DeviceType getDeviceType() {
		return ledSensor.getDeviceType();
	}

	@Override
	public Object getDevice() {
		return this.ledSensor;
	}

	@Override
	public boolean equals(Device<?> device) {
		if(device.getDevice() instanceof DeviceModel) {
			DeviceModel received = (DeviceModel) device.getDevice();
			if(received.getDeviceName().equals(ledSensor.getDeviceName()) && received.getDeviceType() == ledSensor.getDeviceType() 
					&& received.getResourceName().equals(ledSensor.getResourceName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public DeviceModel getDeviceModel() {
		return this.ledSensor;
	}

}
