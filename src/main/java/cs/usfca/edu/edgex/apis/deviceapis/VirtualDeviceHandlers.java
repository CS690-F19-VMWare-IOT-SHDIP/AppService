package cs.usfca.edu.edgex.apis.deviceapis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.reflections.Reflections;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.VirtualRandomModDevice;
import cs.usfca.edu.edgex.device.physicaldevices.PhysicalDevice;
import cs.usfca.edu.edgex.model.RandomModInput;

public class VirtualDeviceHandlers {

	private static Map<String, Device<?>> virtualDevices = new HashMap<String, Device<?>>();
	private static Map<String, Class<?>> virtualDeviceTypes;
	
	/**
	 * Returns list of supported virtual types.
	 * @return Set<String>
	 */
	public static Set<String> listVirtualDeviceTypes() {
		if(virtualDeviceTypes == null) {
			virtualDeviceTypes = new HashMap<String, Class<?>>();
			Reflections reflections = new Reflections("cs.usfca.edu.edgex");    
	    	Set<Class<? extends Device>> virtual = reflections.getSubTypesOf(Device.class);
	    	Set<Class<? extends PhysicalDevice>> physical = reflections.getSubTypesOf(PhysicalDevice.class);
	    	for(Class<? extends Device> clazz : virtual) {
	    		if(!clazz.isInterface() && !physical.contains(clazz))
	    			virtualDeviceTypes.put(clazz.getSimpleName(), clazz);
	    	}
		}
		return virtualDeviceTypes.keySet();
	}
	
	/**
	 * Adds new random mod virtual device.
	 * @param input
	 * @return String
	 */
	public static String addRandomModDevice(RandomModInput input) {
		VirtualRandomModDevice randomModDevice = new VirtualRandomModDevice(input);
		String key = getKeyForValue(randomModDevice);
		if(key != null) {
			return key;
		}
		String randomModDeviceId = UUID.randomUUID().toString();
		virtualDevices.put(randomModDeviceId, randomModDevice);
		return randomModDeviceId;
	}
	
	/**
	 * Returns key for a given device value.
	 * @param device
	 * @return String
	 */
	private static String getKeyForValue(Device<?> device) {
		for(String i : virtualDevices.keySet()) {
			if(virtualDevices.get(i).equals(device))
				return i;
		}
		return null;
	}
	
	/**
	 * Returns list of all virtual devices.
	 * @return Map<String, Device<?>>
	 */
	public static Map<String, Device<?>> listAllVirtualDevices() {
		return virtualDevices;
	}
	
	/**
	 * Returns device with provided deviceId.
	 * @param deviceId
	 * @return Device<?>
	 */
	public static Device<?> getDeviceWithId(String deviceId) {
		return virtualDevices.get(deviceId);
	}
	
	/**
	 * Returns map of virtual devices with device Id.
	 * @return Map<String, Device<?>>
	 */
	public static Map<String, Device<?>> getVirtualDevices() {
		return new HashMap<String, Device<?>>(virtualDevices);
	}
	
	public static String getVirtualDeviceID(Device<?> device) {
		String ID = null;
		for(Map.Entry<String, Device<?>> entry : getVirtualDevices().entrySet()) {
			if(entry.getValue().equals(device)) {
				ID = entry.getKey();
				break;
			}
		}
		return ID;
	}
}
