package cs.usfca.edu.edgex.device;

import cs.usfca.edu.edgex.model.DeviceModel;

/**
 * Device for device type Bulb.
 */
public class BulbDevice implements Device<Boolean> {
	
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
		return value;
	}
	
	/**
	 * Execute set api call on device-service.
	 * This will set the state of current Bulb device.
	 * @param value
	 */
	public void set(Boolean value) {
		System.out.println("Setting Bulb to: " + value);
		this.value = value;
	}

	public DeviceType getDeviceType() {
		return DeviceType.BULB;
	}

}
