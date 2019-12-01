package cs.usfca.edu.edgex.aggregateapis;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.reflections.Reflections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cs.usfca.edu.edgex.apis.deviceapis.DeviceHandlers;
import cs.usfca.edu.edgex.apis.deviceapis.VirtualDeviceHandlers;
import cs.usfca.edu.edgex.apis.eventapis.EventHandlers;
import cs.usfca.edu.edgex.apis.flowapis.FlowHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.physicaldevices.PhysicalDevice;
import cs.usfca.edu.edgex.edgexclient.DeviceID;
import cs.usfca.edu.edgex.edgexclient.EdgeXClient;
import cs.usfca.edu.edgex.exceptions.EventNotFoundException;
import cs.usfca.edu.edgex.exceptions.FlowNotFoundException;
import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.model.FlowModel;
import cs.usfca.edu.edgex.model.Model;
import cs.usfca.edu.edgex.utils.ApiInputMode;

public class InputModelHandlers {
	static String separator = "|";
	private static Reflections reflections;
	private static Reflections reflectionsVirtualModels;
	private static List<String> eventIds = new ArrayList<String>();
	private static Gson gson = new Gson();
	
	public InputModelHandlers() {
		
	}
	
	/**
	 * reads the JSON input and creates devices, events and flow 
	 * @param model
	 * @throws EventNotFoundException
	 * @throws InvalidInputException
	 */
	public static String processInput(InputModel model) throws EventNotFoundException, 
	InvalidInputException, FlowNotFoundException {
		String[] devices = model.devices.split(Pattern.quote(separator));
		List<String> eventNames = model.events;
		List<String> deviceTypes = model.deviceType;
		String flowId = "";
		if(devices.length == 0 || eventNames.size() == 0 || deviceTypes.size() == 0 || 
				devices.length != eventNames.size() || devices.length != deviceTypes.size()
				|| eventNames.size() != deviceTypes.size()) {
			throw new InvalidInputException("Size of devices, events and deviceType must be the same!");
		}
		createDevicesAndEvents(devices, deviceTypes, eventNames);
		FlowModel flowModel = model.flow;
		flowId = FlowHandlers.addFlow(flowModel, ApiInputMode.AggregateAPI);
		// reset eventId list once input from a file is processed completely
		eventIds = new ArrayList<String>();
		return flowId;
	}
	
	/**
	 * creates devices and events
	 * @param devices
	 * @param deviceTypes
	 * @param eventNames
	 * @throws EventNotFoundException
	 * @throws InvalidInputException
	 */
	public static void createDevicesAndEvents(String[] devices, List<String> deviceTypes, List<String> eventNames) throws EventNotFoundException, InvalidInputException {
		try {
			for(int i=0; i<deviceTypes.size(); i++) {
				boolean deviceCreatedSuccessfully = false;
				String deviceType = deviceTypes.get(i);
				String deviceJson = devices[i];
				String eventName = eventNames.get(i);
				String deviceId = "";
				
				reflections = new Reflections("cs.usfca.edu.edgex");
				reflectionsVirtualModels = new Reflections("cs.usfca.edu.edgex.model");
				Set<Class<? extends Model>> virtualModels =  reflections.getSubTypesOf(Model.class);
				Set<Class<? extends DeviceModel>> physicalModels =  reflections.getSubTypesOf(DeviceModel.class);
		    	Set<Class<? extends Device>> virtual = reflections.getSubTypesOf(Device.class);
		    	Set<Class<? extends PhysicalDevice>> physical = reflections.getSubTypesOf(PhysicalDevice.class);
		    	for(Class<? extends Device> clazz : virtual) {
		    		if(!clazz.isInterface() && !physical.contains(clazz) && clazz.getSimpleName().toLowerCase().contains(deviceType.toLowerCase())) {
		    			System.out.println("It's a virtual device"+ " Device Class name: " + clazz.getSimpleName());
		    			for(Class<? extends Model> vm : virtualModels) {
		    				if(vm.getSimpleName().toLowerCase().contains(deviceType.toLowerCase())) {
		    					System.out.println("Found the model! Name: " + vm.getSimpleName());
		    					//read the json file into the vm type here. Something like Model m = Gson.fromGson(String json);
		    					Constructor<?> c = vm.getConstructor();
		    					Type obj = TypeToken.getParameterized(vm, c.newInstance().getClass()).getType();
		    					Model virtualModel = gson.fromJson(deviceJson, obj);
		    					Constructor <?> cons = clazz.getConstructor(Model.class);
		    					Device<?> virtualDevice = (Device<?>) cons.newInstance(virtualModel);
		    					deviceId = registerVirtualDevice(virtualDevice);
		    					deviceCreatedSuccessfully = true;
		    					break;
		    				}
		    			}
		    		}
		    	}
		    	for(Class<? extends PhysicalDevice> clazz : physical) {
		    		if(!clazz.isInterface() && clazz.getSimpleName().toLowerCase().contains(deviceType.toLowerCase())) {
		    			for(Class<? extends DeviceModel> dm : physicalModels) {
		    				if(dm.getSimpleName().toLowerCase().contains(deviceType.toLowerCase())) {
		    					System.out.println("Found the model! Name: " + dm.getSimpleName());
		    					Constructor<?> c = dm.getConstructor();
		    					Type obj = TypeToken.getParameterized(dm, c.newInstance().getClass()).getType();
		    					DeviceModel physicalModel = gson.fromJson(deviceJson, obj);
		    					Constructor <?> cons = clazz.getConstructor(DeviceModel.class);
		    					PhysicalDevice<?> device = (PhysicalDevice<?>) cons.newInstance(physicalModel);
				    			deviceId = registerPhysicalDevice(device);
				    			deviceCreatedSuccessfully = true;
				    			break;
		    				}
		    			}
		    		}
		    	}
		    	if(!deviceCreatedSuccessfully || deviceId.isEmpty()) {
		    		throw new InvalidInputException("Device: " + deviceJson + " from input JSON is invalid");
		    	}
		    	String eventId = bindEventToDevice(deviceId, eventName);
		    	System.out.println(deviceId + " : " + eventName + " : " + eventId);
		    	eventIds.add(eventId);
			}
		}
		catch(InstantiationException | IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException | NoSuchMethodException | SecurityException ex) {
			System.out.println("error creatingDevicesAndEvents dynamically: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Registers new physical device and returns deviceId.
	 * @param deviceModel
	 * @return String
	 */
	public static String registerPhysicalDevice(PhysicalDevice<?> device) {
		String key = DeviceHandlers.getKeyForValue(device);
		if(key != null) {
			return key;
		}
		if(device.getDeviceType() != null) {
			// ONLY uncomment these three lines below when testing and not connected to EdgeX
			DeviceID edeviceId = gson.fromJson(EdgeXClient.getDeviceID(device.getDeviceModel().getDeviceName()), DeviceID.class);
			device.getDeviceModel().setDeviceID(edeviceId.getDeviceID());
			String deviceId = UUID.randomUUID().toString();
			DeviceHandlers.addPhysicalDevice(deviceId, device);
			//return deviceId;
			//TODO: Comment three lines above & Uncomment three lines below after testing
			//DeviceHandlers.addPhysicalDevice(deviceId.getDeviceID(), device);
			return deviceId;
		}
		return null;
	}
	
	/**
	 * Adds new virtual device.
	 * @param input
	 * @return String
	 */
	public static String registerVirtualDevice(Device<?> virtualDevice) {
		String key = VirtualDeviceHandlers.getKeyForValue(virtualDevice);
		if(key != null) {
			return key;
		}
		String deviceId = UUID.randomUUID().toString();
		VirtualDeviceHandlers.addVirtualDeviceToMap(deviceId, virtualDevice);
		return deviceId;
	}
	
	/**
	 * Check if given deviceID and eventName is valid, else throw concerned errors.
	 * Bind an event to device if possible.
	 * @param eventName
	 * @param deviceID
	 * @return ResponseEntity<?>
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static String bindEventToDevice(String deviceId, String eventName) throws NoSuchMethodException, SecurityException, 
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Device<?> device = EventHandlers.getDeviceFromMap(deviceId);
		String eventID = "";
		if(device != null) {
			Set<String> eventsWithDevice = EventHandlers.listEventsWithType(device.getDeviceType());
			if(eventsWithDevice.contains(eventName)) {
				eventID = EventHandlers.bindEventToDevice(eventName, device);
			}
		}
		return eventID;
	}
	
	public static List<String> getEventIDs() {
		return eventIds;
	}
}
