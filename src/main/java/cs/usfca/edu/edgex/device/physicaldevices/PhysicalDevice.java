package cs.usfca.edu.edgex.device.physicaldevices;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.model.DeviceModel;

public interface PhysicalDevice<T> extends Device<T> {
	/**
	 * Returns deviceModel of device.
	 */
	public DeviceModel getDeviceModel();
}
