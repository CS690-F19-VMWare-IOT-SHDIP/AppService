package cs.usfca.edu.edgex.device.physicaldevices;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
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
	
	/**
	 * Returns the type of device this class represents.
	 * @return DeviceType
	 */
	public DeviceType getDeviceType() {
		return bulb.getDeviceType();
	}
	
	/**
	 * Get impelemented device.
	 */
	public Object getDevice() {
		return bulb;
	}

	@Override
	public DeviceModel getDeviceModel() {
		return bulb;
	}

}
