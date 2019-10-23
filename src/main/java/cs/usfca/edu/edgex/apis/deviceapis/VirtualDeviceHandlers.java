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
	
	public static String addRandomModDevice(RandomModInput input) {
		// TODO: check if the device with similar configurations is present before adding it to virtualDevices.
		VirtualRandomModDevice randomModDevice = new VirtualRandomModDevice(input);
		String randomModDeviceId = UUID.randomUUID().toString();
		virtualDevices.put(randomModDeviceId, randomModDevice);
		return randomModDeviceId;
	}
	
	public static Map<String, Device<?>> listAllVirtualDevices() {
		return virtualDevices;
	}
	
	public static Device<?> listDeviceWithId(String deviceId) {
		return virtualDevices.get(deviceId);
	}
}
