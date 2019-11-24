package cs.usfca.edu.edgex.apis.deviceapis;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.apis.flowapis.FlowHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.physicaldevices.PhysicalDevice;
import cs.usfca.edu.edgex.edgexclient.DeviceID;
import cs.usfca.edu.edgex.edgexclient.EdgeXClient;
import cs.usfca.edu.edgex.model.DeviceModel;

public class DeviceHandlers {
	private static Gson gson = new Gson();
	private static ConcurrentHashMap<String, PhysicalDevice<?>> physicalDevices = new ConcurrentHashMap<String, PhysicalDevice<?>>();
	
	/**
	 * Returns list of physical devices bind to a deviceId.
	 * @return Map<String, DeviceModel>
	 */
	public static Map<String, DeviceModel> getAllPhysicalDevices() {
		Map<String, DeviceModel> deviceModelMap = new HashMap<String, DeviceModel>();
		for(String key : physicalDevices.keySet()) {
			deviceModelMap.put(key, physicalDevices.get(key).getDeviceModel());
		}
		return deviceModelMap;
	}
	
	/**
	 * Registers new physical device and returns deviceId.
	 * @param deviceModel
	 * @return String
	 */
	public static String registerPhysicalDevice(DeviceModel deviceModel) {
		PhysicalDevice<?> device = DeviceTypeToDeviceMap.getPhysicalDevice(deviceModel);
		String key = getKeyForValue(device);
		if(key != null) {
			return key;
		}
		if(device.getDeviceType() != null) {
			DeviceID deviceId = gson.fromJson(EdgeXClient.getDeviceID(device.getDeviceModel().getDeviceName()), DeviceID.class);
			physicalDevices.put(deviceId.getDeviceID(), device);
			return deviceId.getDeviceID();
		}
		return null;
	}
	
	/**
	 * Adds a physical device to the device map.
	 * @param deviceId
	 * @param device
	 */
	public static void addPhysicalDevice(String deviceId, PhysicalDevice<?> device) {
		physicalDevices.put(deviceId, device);
	}
	
	/**
	 * Returns key for given device.
	 * @param device
	 * @return String
	 */
	public static String getKeyForValue(Device<?> device) {
		for(String i : physicalDevices.keySet()) {
			if(physicalDevices.get(i).equals(device))
				return i;
		}
		return null;
	}
	
	/**
	 * Returns deviceModel for given physical deviceId.
	 * @param deviceId
	 * @return DeviceModel
	 */
	public static DeviceModel getPhysicalDeviceForDeviceId(String deviceId) {
		PhysicalDevice<?> device = physicalDevices.get(deviceId);
		if(device != null) {
			return device.getDeviceModel();
		}
		return null;
	}
	
	/**
	 * Returns list of physcial devices with given deviceType.
	 * @param deviceType
	 * @return Map<String, DeviceModel>
	 */
	public static Map<String, DeviceModel> listPhysicalDeviceWithType(DeviceType deviceType) {
		Map<String, DeviceModel> deviceModelMap = new HashMap<String, DeviceModel>();
		for(String key : physicalDevices.keySet()) {
			if(physicalDevices.get(key).getDeviceType() == deviceType)
				deviceModelMap.put(key, physicalDevices.get(key).getDeviceModel());
		}
		return deviceModelMap;
	}
	
	/**
	 * Returns list of deviceTypes.
	 * @return List<DeviceType>
	 */
	public static List<DeviceType> listDeviceTypes() {
		return new LinkedList<DeviceType>(EnumSet.allOf(DeviceType.class));
	}
	
	/**
	 * Returns map of physical devices with deviceId.
	 * @return Map<String, PhysicalDevice<?>>
	 */
	public static Map<String, PhysicalDevice<?>> getPhysicalDevices() {
		return new HashMap<String, PhysicalDevice<?>>(physicalDevices);
	}
	
	/**
	 * Returns the device id for physiscal device.
	 * @param device
	 * @return
	 */
	public static String getPhysicalDeviceID(PhysicalDevice<?> device) {
		String ID = null;
		for(Map.Entry<String, PhysicalDevice<?>> entry : getPhysicalDevices().entrySet()) {
			if(entry.getValue().equals(device)) {
				ID = entry.getKey();
				break;
			}
		}
		return ID;
	}
	
	/**
	 * Removes device with provided deviceId.
	 * @param deviceId
	 * @return
	 */
	public static boolean removeDevice(String deviceId) {
		PhysicalDevice<?> device = physicalDevices.get(deviceId);
		if(device == null || FlowHandlers.isDeviceOccupied(device))
			return false;
		physicalDevices.remove(deviceId);
		return true;
	}
	
}
