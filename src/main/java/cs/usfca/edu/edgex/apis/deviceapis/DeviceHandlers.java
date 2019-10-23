package cs.usfca.edu.edgex.apis.deviceapis;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.reflections.Reflections;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.physicaldevices.PhysicalDevice;
import cs.usfca.edu.edgex.model.DeviceModel;

public class DeviceHandlers {

	private static Map<String, PhysicalDevice<?>> physicalDevices = new HashMap<String, PhysicalDevice<?>>();
	
	public static Map<String, DeviceModel> getAllPhysicalDevices() {
		Map<String, DeviceModel> deviceModelMap = new HashMap<String, DeviceModel>();
		for(String key : physicalDevices.keySet()) {
			deviceModelMap.put(key, physicalDevices.get(key).getDeviceModel());
		}
		return deviceModelMap;
	}
	
	public static String registerPhysicalDevice(DeviceModel deviceModel) {
		PhysicalDevice<?> device = DeviceTypeToDeviceMap.getPhysicalDevice(deviceModel);
		if(device.getDeviceType() != null) {
			String deviceId = UUID.randomUUID().toString();
			physicalDevices.put(deviceId, device);
			return deviceId;
		}
		return null;
	}
	
	public static DeviceModel getPhysicalDeviceForDeviceId(String deviceId) {
		return physicalDevices.get(deviceId).getDeviceModel();
	}
	
	public static Map<String, DeviceModel> listPhysicalDeviceWithType(DeviceType deviceType) {
		Map<String, DeviceModel> deviceModelMap = new HashMap<String, DeviceModel>();
		for(String key : physicalDevices.keySet()) {
			if(physicalDevices.get(key).getDeviceType() == deviceType)
				deviceModelMap.put(key, physicalDevices.get(key).getDeviceModel());
		}
		return deviceModelMap;
	}
	
	public static List<DeviceType> listDeviceTypes() {
		return new LinkedList<DeviceType>(EnumSet.allOf(DeviceType.class));
	}
	
	public static Map<String, PhysicalDevice<?>> getPhysicalDevices() {
        return new HashMap<String, PhysicalDevice<?>>(physicalDevices);
    }
	
}
