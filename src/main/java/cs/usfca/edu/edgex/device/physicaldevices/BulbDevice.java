package cs.usfca.edu.edgex.device.physicaldevices;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.apis.deviceapis.DeviceHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.edgexclient.DeviceEvent;
import cs.usfca.edu.edgex.edgexclient.EdgeXClient;
import cs.usfca.edu.edgex.edgexclient.Readings;
import cs.usfca.edu.edgex.model.DeviceModel;

/**
 * Device for device type Bulb.
 */
public class BulbDevice implements PhysicalDevice<Boolean> {
	
	private DeviceModel bulb;
	private Boolean value;
	
	
	public BulbDevice(DeviceModel bulb) {
		this.bulb = bulb;
		this.value = false;
	}

	/**
	 * Execute get api call on device-service.
	 * This will return the state of current Bulb device.
	 * @return Boolean
	 */
	public Boolean get() {
		System.out.println("Bulb is: " + value);
		String deviceID = DeviceHandlers.getPhysicalDeviceID(this);
		String responseData = EdgeXClient.get(this.bulb, deviceID);
		DeviceEvent[] reading = new Gson().fromJson(responseData.toString(), DeviceEvent[].class);
		
		this.value = (reading[0].getReadings().get(0).getValue() == "true") ? true : false;
		System.out.println("Bulb is: " + value);

		return value;
	}
	
	/**
	 * Execute set api call on device-service.
	 * This will set the state of current Bulb device.
	 * @param value
	 */
	public void set(Boolean value) {
		System.out.println("Setting Bulb to: " + value);
		String deviceID = DeviceHandlers.getPhysicalDeviceID(this);

		String val = (value == true) ? "true" : "false";
		JSONObject putBody = new JSONObject().put(this.bulb.getResourceName(), val);
		String responseData = EdgeXClient.put(this.bulb, putBody.toString(), deviceID);
		this.value = value;
	}
	
	/**
	 * Returns the type of device this class represents.
	 * @return DeviceType
	 */
	public DeviceType getDeviceType() {
		return bulb.getDeviceType();
	}
	
	/**
	 * Get impelemented device.
	 * @return Object
	 */
	public Object getDevice() {
		return bulb;
	}
	
	/**
	 * Returns physical device input model.
	 * @return DeviceModel
	 */
	@Override
	public DeviceModel getDeviceModel() {
		return bulb;
	}
	
	/**
	 * Equals method to compare current device with other deviceTypes.
	 * @param device
	 * @return boolean
	 */
	@Override
	public boolean equals(Device<?> device) {
		if(device.getDevice() instanceof DeviceModel) {
			DeviceModel received = (DeviceModel) device.getDevice();
			System.out.println("Casted device to DeviceModel");
			if(received.getDeviceName().equals(bulb.getDeviceName()) && received.getDeviceType() == bulb.getDeviceType() 
					&& received.getResourceName().equals(bulb.getResourceName())) {
				return true;
			}
		}
		return false;
	}
	
	public DeviceEvent createReading(String value) {
		ArrayList<Readings> readings = new ArrayList<>();
		readings.add(new Readings(this.bulb.getResourceName(), value));
		
		return new DeviceEvent(this.bulb.getDeviceName(), readings);
	}

}
