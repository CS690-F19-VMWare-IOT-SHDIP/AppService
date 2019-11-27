package cs.usfca.edu.edgex.apis.deviceapis;

import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.physicaldevices.BulbDevice;
import cs.usfca.edu.edgex.device.physicaldevices.PhysicalDevice;
import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.DeviceModel;

public class DeviceTypeToDeviceMap {
	
	public static PhysicalDevice<?> getPhysicalDevice(DeviceModel deviceModel) throws InvalidInputException {
		if (deviceModel.getDeviceType() == DeviceType.BULB) {
			return new BulbDevice(deviceModel);
		}
		return null;
	}
}
